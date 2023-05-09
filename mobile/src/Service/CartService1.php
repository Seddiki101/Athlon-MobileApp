<?php

namespace App\Service;

use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Psr\Log\LoggerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Security;


class CartService1
{

    private $logger;

    protected $session;
    protected $produitRepository;
    protected $commandeRepo;
    protected $commandeitemRepo;
    protected $entityManager;
    protected $request;
    private $security;

    public function __construct(Security $security,LoggerInterface $logger, SessionInterface $session, \App\Repository\ProduitRepository $produitRepository, \App\Repository\CommandeRepository $commandeRepository, \App\Repository\CommandeItemRepository $commandeItemRepository, EntityManagerInterface $entityManager)
    {
        $this->logger = $logger;

        $this->session = $session;
        $this->produitRepository = $produitRepository;
        $this->commandeRepo = $commandeRepository;
        $this->commandeitemRepo = $commandeItemRepository;
        $this->entityManager = $entityManager;

        $this->security = $security;


    }

    public function remove(int $id)
    {

        $cart = $this->session->get('cart', []);

        $commandeitem = new \App\Entity\CommandeItem();
        $commandeitem->setQuantity($cart[$id])
            ->setProduit($this->produitRepository->find($id))
            ->setCommande($this->getCurrentOrder());
        CommandeItemService::delete($commandeitem, $this->commandeitemRepo, $this->entityManager);

        if (!empty($cart[$id]))
            unset($cart[$id]);
        $this->session->set('cart', $cart);
        $this->session->migrate();
    }


    public function removeV2(int $id,int $cin)
    {

        $cart = $this->session->get('cart', []);

        $commandeitem = new \App\Entity\CommandeItem();
        $commandeitem->setQuantity($cart[$id])
            ->setProduit($this->produitRepository->find($id))
            ->setCommande($this->getCurrentOrderV2($cin));
        CommandeItemService::delete($commandeitem, $this->commandeitemRepo, $this->entityManager);

        if (!empty($cart[$id]))
            unset($cart[$id]);
        $this->session->set('cart', $cart);
        $this->session->migrate();
    }


    public function add(int $id)
    {

        $cart = $this->session->get('cart', []);
        if (!empty($cart[$id]))
            $cart[$id]++;
        else
            $cart[$id] = 1;

        $commandeItem = new \App\Entity\CommandeItem();
        $commandeItem->setQuantity($cart[$id])
            ->setProduit($this->produitRepository->find($id))
            ->setCommande($this->getCurrentOrder());


        CommandeItemService::insertOrUpdate($commandeItem, $this->commandeitemRepo, $this->entityManager);
        $this->session->set('cart', $cart);

    }


    public function addV2(int $id,int $cin)
    {

        $cart = $this->session->get('cart', []);
        if (!empty($cart[$id]))
            $cart[$id]++;
        else
            $cart[$id] = 1;

        $commandeItem = new \App\Entity\CommandeItem();
        $commandeItem->setQuantity($cart[$id])
            ->setProduit($this->produitRepository->find($id))
            ->setCommande($this->getCurrentOrderV2($cin));


        CommandeItemService::insertOrUpdate($commandeItem, $this->commandeitemRepo, $this->entityManager);
        $this->session->set('cart', $cart);

    }



    public function clear()
    {
        $this->session->set('cart', []);

    }


    public function getCurrentOrder(): \App\Entity\Commande
    {
        $user = $this->security->getUser();

        return $this->session->get('currentOrder', $this->commandeRepo->findOneBy([
            'user' =>  $user->getId(),
            'statue' => 'pending'
        ]));
    }




    public function getCurrentOrderV2($cin): \App\Entity\Commande
    {


        return $this->session->get('currentOrder', $this->commandeRepo->findOneBy([
            'user' =>  $cin,
            'statue' => 'pending'
        ]));
    }





    public function getCart(): array
    {

        $cart = $this->session->get('cart', []);


        if (!$cart)
            return $this->initCart();

        $cartWithData = [];
        foreach ($cart as $id => $quantity) {
            $cartWithData[] = [
                'product' => $this->produitRepository->find($id),
                'quantity' => $quantity
            ];
        }
        return $cartWithData;
    }

    public function getTotal(): float
    {

        $total = 0;
        foreach ($this->getCart() as $item)
            $total += $item['product']->getPrix() * $item['quantity'];

        return $total;
    }

    public function initCart()
    {

        $cartWithData = [];
        $item = new \App\Entity\CommandeItem();
        foreach ($this->commandeitemRepo->findBy(['commande' => $this->getCurrentOrder()]) as $item) {
            $cartWithData[] = [
                'product' => $item->getProduit(),
                'quantity' => $item->getQuantity()
            ];
            $cart[$item->getProduit()->getId()] = $item->getQuantity();
            $this->session->set('cart', $cart);
        }
        return $cartWithData;
    }


}