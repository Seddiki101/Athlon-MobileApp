<?php

namespace App\Controller;

use App\Entity\Reclamation;
use App\Repository\ReclamationRepository;

use App\Entity\User;
use App\Repository\UserRepository;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;

use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\HttpFoundation\JsonResponse;


class ReclamationServiceController extends AbstractController
{
 
#[Route("/AllRecs", name: "list")]

    public function getReclamations(ReclamationRepository $repo, SerializerInterface $serializer)
    {
        $lista = $repo->findAll();

        $json = $serializer->serialize($lista, 'json', ['groups' => "reclamations"]);

        return new Response($json);
    }
	
	
	
	
	
	
	
	
	#[Route("/AlluserRecs/{idu}", name: "listUsRecs")]

    public function getReclamationUsr($idu,ReclamationRepository $repo, SerializerInterface $serializer , UserRepository $repos)
    {
		

		$user = $repos->find($idu);
		
        $lista = $user->getReclamationX();
		
		// if this don t work 
		// this being getting user id from session and finding his recs
		//do the opposite , find all reqs having user id that u looking for (get it as paramter from request)

        $json = $serializer->serialize($lista, 'json', ['groups' => "reclamations"]);

        return new Response($json);
    }
	
		
		
		
		
		//this works man //hamdou li allah
			#[Route("/AlluserRec/{idu}", name: "listUsRecs2")]

    public function getReclamationUsr2( $idu,ReclamationRepository $repo, SerializerInterface $serializer , UserRepository $repos)
    {
		
		//$userid  = $req->query->get("idu");
		$user = $repos->find($idu);
		
        $lista = $user->getReclamationX();
		
		// if this don t work 
		// this being getting user id from session and finding his recs
		//do the opposite , find all reqs having user id that u looking for (get it as paramter from request)

        $json = $serializer->serialize($lista, 'json', ['groups' => "reclamations"]);

        return new Response($json);
    }
	
	

    #[Route("/recs/{id}", name: "recs")]
    public function ReclamationId($id, NormalizerInterface $normalizer, ReclamationRepository $repo)
    {
        $student = $repo->find($id);
        $studentNormalises = $normalizer->normalize($student, 'json', ['groups' => "reclamations"]);
        return new Response(json_encode($studentNormalises));
    }


    #[Route("addRecJSON/new", name: "addReclamationJSON")]
    public function addReclamationJSON(Request $req,   NormalizerInterface $Normalizer , UserRepository $repo)
    {
		
		  $em = $this->getDoctrine()->getManager();
        $student = new Reclamation();
        $student->setTitre($req->get('titre'));
        $student->setDesipticon($req->get('desipticon'));

//				$userid  = $req->query->get("idu");
//                $userid = 5;
//
//                $user = $repo->find($userid);
//				if ($user instanceof \App\Entity\User) {
//                    $student->setUserX($user);
//				}



        $em->persist($student);
        $em->flush();

        $jsonContent = $Normalizer->normalize($student, 'json', ['groups' => 'reclamations']);
        return new Response(json_encode($jsonContent));
    }



    #[Route("updateRecJSON/mod", name: "updateReclamationJSON")]
    public function updateReclamationJSON(Request $req, NormalizerInterface $Normalizer)
    {
        $id=$req->get('id');
        $em = $this->getDoctrine()->getManager();
        $student = $em->getRepository(Reclamation::class)->find($id);
        $student->setTitre($req->get('titre'));
        $student->setDesipticon($req->get('desipticon'));

        $em->flush();

        $jsonContent = $Normalizer->normalize($student, 'json', ['groups' => 'reclamations']);
        return new Response(" updated successfully " . json_encode($jsonContent));
    }



    #[Route("deleteRecJSON/{id}", name: "deleteReclamationJSON")]
    public function deleteReclamationJSON(Request $req, $id, NormalizerInterface $Normalizer)
    {

        $em = $this->getDoctrine()->getManager();
        $student = $em->getRepository(Reclamation::class)->find($id);
        $em->remove($student);
        $em->flush();
        $jsonContent = $Normalizer->normalize($student, 'json', ['groups' => 'reclamations']);
        return new Response(" deleted successfully " . json_encode($jsonContent));
    }
 
}
