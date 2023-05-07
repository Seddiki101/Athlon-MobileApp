<?php

namespace App\Controller;
//namespace App\Service;

use App\Entity\Sujet;
use App\Form\SujetType;
use App\Repository\SujetRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;


#[Route('/mobile/sujet')]
class SujetMobileController extends AbstractController
{
    #[Route('/', name: 'app_sujet_index_mobile_Mobile', methods: ['GET'])]
    public function index(SujetRepository $sujetRepository, NormalizerInterface $normalizable): Response
    {

        $sujet = $sujetRepository->findAll();
        $jsonContent = $normalizable->normalize($sujet, 'json', ['groups' => ['sujet', 'article']]);
        return new \Symfony\Component\HttpFoundation\JsonResponse($jsonContent);
    }

    #[Route('/new', name: 'app_sujet_new_Mobile', methods: ['GET', 'POST'])]
    public function new(Request $request, SujetRepository $sujetRepository): Response
    {
        $sujet = new Sujet();

        $sujet->setNom($request->get("nom"));
        $sujet->setDescr($request->get("description"));
        $sujet->setImgSujet($request->get("img"));
        $sujetRepository->save($sujet, true);

        return new \Symfony\Component\HttpFoundation\JsonResponse("success");

    }


    #[Route('/{id}', name: 'app_sujet_delete_Mobile', methods: ['POST'])]
    public function delete(Request $request, Sujet $sujet, SujetRepository $sujetRepository): Response
    {
        $sujetRepository->remove($sujet, true);

        return new \Symfony\Component\HttpFoundation\JsonResponse("success");
    }
}
