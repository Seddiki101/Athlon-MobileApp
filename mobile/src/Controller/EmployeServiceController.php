<?php

namespace App\Controller;


use App\Entity\Employe;
use App\Form\EmployeType;
use App\Repository\EmployeRepository;


use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\SerializerInterface;


class EmployeServiceController extends AbstractController
{
    #[Route('/employe/service', name: 'app_employe_service')]
    public function index(): Response
    {
        return $this->render('employe_service/index.html.twig', [
            'controller_name' => 'EmployeServiceController',
        ]);
    }
    
	///////////////////////////////////////////////////Affichage liste////////////////////////////////////////////////////////////
	#[Route("/AllUserService", name: "listUserS")]
	public function getStudents(EmployeRepository $repo, SerializerInterface $serializer)
    {
		$lista = $repo->findAll();
		$json = $serializer->serialize($lista, 'json', ['groups' => "employes"]);
		    return new Response($json);
    }
}


