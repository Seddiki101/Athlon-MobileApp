<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;


/**
 * @Route("/mobile")
 */
class CardControllerMobile extends AbstractController
{

    /**
     * @Route("/cart", name="cart_mobile")
     */
    public function index(\App\Service\CartService1 $cartService, NormalizerInterface $normalizable): Response
    {

        $cart = $cartService->getCart();
        //$jsonContent = $normalizable->normalize($cart, 'json', ['groups' => 'post:read'] ,"produit" );
        $jsonContent = $normalizable->normalize($cart, 'json', ['groups' => ['post:read', 'Produit']]);


        return new \Symfony\Component\HttpFoundation\JsonResponse($jsonContent);
    }

    /**
     * @Route("/cart/add/{id}/{cin}", name="cart_add_mobile")
     */
    public function add($id,$cin, \App\Service\CartService1 $cartService)
    {
       // $cartService->getCurrentOrderV2($cin);
        $cartService->addV2($id,$cin);
        return new Response("Item jouter au cart !", Response::HTTP_OK);
    }
//
//    /**
//     * @Route("/listproduit", name="produit_list")
//     */
//    public function list(\App\Repository\ProduitRepository $repository)
//    {
//        $produit = $repository->findAll();
//        return $this->render('produit/index.html.twig', ['produits' => $produit]);
//
//    }
//
    /**
     * @Route("/cart/remove2/{id}/{cin}", name="cart_remove_mobile2")
     */
    public function remove2($id,$cin, \App\Service\CartService1 $cartService)
    {

        $cartService->removeV2($id,$cin);

        return new Response("Item surimer de cart !", Response::HTTP_OK);
    }


    /**
     * @Route("/cart/remove/{id}", name="cart_remove_mobile")
     */
    public function remove($id, \App\Service\CartService1 $cartService)
    {

        $cartService->remove($id);

        return new Response("Item surimer de cart !", Response::HTTP_OK);
    }


}