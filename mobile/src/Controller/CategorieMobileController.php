<?php

namespace App\Controller;
//namespace App\Service;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Categorie;
use Doctrine\Persistence\ManagerRegistry;
use App\Repository\CategorieRepository;
use App\Repository\ProduitRepository;
use App\Form\CategorieType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\String\Slugger\SluggerInterface;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Psr\Log\LoggerInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;



class CategorieMobileController extends AbstractController
{

    #[Route('/mobile/Categorieaff', name: 'afficherCat_mobile_mobile')]
    public function afficherCategorie(ManagerRegistry $em, NormalizerInterface $normalizable): Response
    {
        $repo=$em->getRepository(Categorie::class);
        $result=$repo->findAll();
        $jsonContent = $normalizable->normalize($result, 'json', ['groups' => ['cat','Produit']]);
        return new \Symfony\Component\HttpFoundation\JsonResponse($jsonContent);
    }





    #[Route('/mobile/Categorie/add', name: 'CategorieAdd_mobile')]
    public function addCategorie(ManagerRegistry $doctrine,Request $request,SluggerInterface $slugger): Response

    {
        $Categorie=new Categorie() ;
        $Categorie->setNom($request->get("nom"));
        $Categorie->setImage($request->get("img"));
            $em=$doctrine->getManager();
            $em->persist($Categorie);
            $em->flush();
        return new Response("produit aff", Response::HTTP_OK);

    }

    #[Route('/mobile/Categorie/update/{id}', name: 'updateCat_mobile')]

    public function  updateCategorie (ManagerRegistry $doctrine,$id,  Request  $request,SluggerInterface $slugger) : Response
    { $Categorie = $doctrine
        ->getRepository(Categorie::class)
        ->find($id);
        $Categorie->setNom($request->get("nom"));
        if($request->get("img")!='null'){
            $Categorie->setImage($request->get("img"));
        }
            $em = $doctrine->getManager();
            $em->flush();
        return new Response("produit aff", Response::HTTP_OK);
    }


    #[Route('/mobile/Categorie/delete/{id}', name: 'deleteCat_mobile')]

    public function delete($id, ManagerRegistry $doctrine)
    {$c = $doctrine
        ->getRepository(Categorie::class)
        ->find($id);
        $em = $doctrine->getManager();
        $em->remove($c);
        $em->flush() ;
        return new Response("produit suprime", Response::HTTP_OK);

    }




}
