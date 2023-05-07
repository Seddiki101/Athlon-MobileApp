<?php

namespace App\Controller;
//namespace App\Service;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Produit;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Component\HttpFoundation\Request;
use Doctrine\ORM\EntityManagerInterface;
use App\Controller\SearchType;
use Endroid\QrCode\Factory\QrCodeFactoryInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use App\Entity\Comments;
use Psr\Log\LoggerInterface;
use DateTime;

use Endroid\QrCode\Builder\BuilderInterface;






 
class ProduitMobileController extends AbstractController
{

    


    private $entityManager;
    #[Route('/manager', name: 'app_manager')]
    public function __construct(EntityManagerInterface $entityManager)
    {
        $this->entityManager = $entityManager;
        
    }

    #[Route('/mobile/Produitaff', name: 'afficher_mobile')]
    public function afficher(ManagerRegistry $em, NormalizerInterface $normalizable): Response
    {
        $repo=$em->getRepository(Produit::class);
        $result=$repo->findAll();

        $jsonContent = $normalizable->normalize($result, 'json', ['groups' => 'Produit']);

        return new \Symfony\Component\HttpFoundation\JsonResponse($jsonContent);
   
    }

    #[Route('/mobile/produit/add', name: 'ProduitAdd_mobile')]
    public function add(ManagerRegistry $doctrine,Request $request, \App\Repository\CategorieRepository $categorieRepository): Response
    
    {
        $Produit=new Produit() ;
        $Produit->setImage($request->get("img"));
        $Produit->setNom($request->get("nom"));
        $Produit->setDescription($request->get("desc"));
        $Produit->setPrix($request->get("prix"));
        $Produit->setBrand($request->get("brand"));
        $Produit->setCategories($categorieRepository->findOneById($request->get("idCat")));

        $em=$doctrine->getManager();
        $em->persist($Produit); 
        $em->flush();
        return new Response("produit ajout success", Response::HTTP_OK);
    }


    #[Route('/mobile/Produit/delete/{id}', name: 'mobile')]

    public function delete($id, ManagerRegistry $doctrine)
    {$c = $doctrine
        ->getRepository(Produit::class)
        ->find($id);
        $em = $doctrine->getManager();
        $em->remove($c);
        $em->flush() ;
        return new Response("produit suprime", Response::HTTP_OK);


    }

    #[Route('/mobile/commentadd/{id}', name: 'commentaddmobile')]
    public function detaillehh($id,ManagerRegistry $mg, Produit $Produit, Request $request,LoggerInterface $logger,BuilderInterface $customQrCodeBuilder): Response
    {
        $em = $this->getDoctrine()->getManager();
        $repo=$mg->getRepository(Produit::class);
        $resultat = $repo ->find($id);
        $result = $customQrCodeBuilder
            ->size(400)
            ->margin(20)
            ->build();

        $comment = new Comments;

        // On génère le formulaire
            $comment->setContent($request->get("content"));
            $comment->setCreatedAt(new DateTime());
            $comment->setProduits($Produit);
            $comment->setParent($parent ?? null);
            $em->persist($comment);
            $em->flush();

        return new Response("Votre commentaire a été bien envoyé", Response::HTTP_OK);


        }
    #[Route('/mobile/Produit/update/{id}', name: 'update_mobile')]

    public function  updateProduit (ManagerRegistry $doctrine,$id,  Request  $request,\App\Repository\CategorieRepository $categorieRepository) : Response
    {
        $Produit = $doctrine
        ->getRepository(Produit::class)
        ->find($id);

        $Produit->setImage($request->get("img"));
        $Produit->setNom($request->get("nom"));
        $Produit->setDescription($request->get("desc"));
        $Produit->setPrix($request->get("prix"));
        $Produit->setBrand($request->get("brand"));
        $Produit->setCategories($categorieRepository->findOneById($request->get("idCat")));
        $em = $doctrine->getManager();
        $em->flush();

        return new Response("produit aff", Response::HTTP_OK);

    }

    #[Route("/mobile/like/{id}/{type}", name:"app_testfront_like_mobile")]

    public function like(Request $request, Produit $Produit, $type)
    {
        if ($type == 'like') {
            $Produit->setLikes($Produit->getLikes() + 1);
        } else {
            $Produit->setDislikes($Produit->getDislikes() + 1);
        }

        $this->getDoctrine()->getManager()->flush();

        return new Response("Votre commentaire a été bien envoyé", Response::HTTP_OK);
    }

}

