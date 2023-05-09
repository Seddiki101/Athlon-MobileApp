<?php

namespace App\Controller;

use App\Entity\Conge;
use App\Form\CongeType;
use App\Repository\CongeRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Doctrine\ORM\EntityManagerInterface;
use App\Repository\EmployeRepository;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;


#[Route('/mobile/conge')]
class CongeControllerMobile extends AbstractController
{
    #[Route('/', name: 'app_conge_index_mobile', methods: ['GET'])]
    public function index(CongeRepository $congeRepository, NormalizerInterface $normalizable): Response
    {
        $conge = $congeRepository->findAll();
  
        $jsonContent = $normalizable->normalize($conge, 'json', ['groups' => 'conge']);
        return new \Symfony\Component\HttpFoundation\JsonResponse($jsonContent);

    }

    #[Route('/new', name: 'app_conge_new_mobile', methods: ['GET', 'POST'])]
    public function new(Request $request, CongeRepository $congeRepository, EntityManagerInterface $entityManager, EmployeRepository $employeRepository, MailerInterface $mailer): Response
    {
        $conge = new Conge();

        $employe = $employeRepository->findOneById($request->get("idEmployee"));
        $conge->setEmploye($employe);

        $mil = $request->get('dateD');
        $seconds = $mil / 1000;
        $dateD = date_create_from_format("d-m-Y h:i:s", date("d-m-Y h:i:s", $seconds));
        $mil = $request->get('DateF');
        $seconds = $mil / 1000;
        $dateF = date_create_from_format("d-m-Y h:i:s", date("d-m-Y h:i:s", $seconds));
        $conge->setDateD($dateD);
        $conge->setDateF($dateF);
        $conge->setType($request->get("type"));
        $congeRepository->save($conge, true);

        $html = "emplyee " . $employe->getNom() . " a un congée de " . $conge->getDateD()->format('Y-m-d') . " à " . $conge->getDateF()->format('Y-m-d');

        $email = (new Email())
            ->from('from@example.com')
            ->to("to@example.com")
            ->subject('Empoyee congé')
            ->html($html);

        $mailer->send($email);

        // Update employe etat
        $conges = $congeRepository->findByDateDebutAndDateFin(new \DateTime());
        $etat = 'Disponible';

        foreach ($conges as $conge) {
            if ($conge->getEmploye() === $employe) {
                $etat = 'Congé';
                break; // exit the loop once a matching conge is found
            }
        }


        $employe->setEtat($etat);

        $employeRepository->save($employe);
        $entityManager->flush();

        return new \Symfony\Component\HttpFoundation\JsonResponse("sucess");

    }




//    #[Route('/{id}', name: 'app_conge_show', methods: ['GET'])]
//    public function show(Conge $conge): Response
//    {
//        return $this->render('conge/show.html.twig', [
//            'conge' => $conge,
//        ]);
//    }

    #[Route('/{id}/edit', name: 'app_conge_edit_mobile', methods: ['GET', 'POST'])]
    public function edit(Request $request, Conge $conge, CongeRepository $congeRepository, EmployeRepository $employeRepository, EntityManagerInterface $entityManager): Response
    {


        $employe = $employeRepository->findOneById($request->get("idEmployee"));
        $conge->setEmploye($employe);
        $conge->setDateD($request->get("dateD"));
        $conge->setDateF($request->get("DateF"));
        $conge->setType($request->get("type"));
        $congeRepository->save($conge, true);

        $conges = $congeRepository->findByDateDebutAndDateFin(new \DateTime());
        $etat = 'Disponible';

        foreach ($conges as $conge) {
            if ($conge->getEmploye() === $employe) {
                $etat = 'Congé';
                break; // exit the loop once a matching conge is found
            }
        }

        $employe->setEtat($etat);
        $employeRepository->save($employe);
        $entityManager->flush();
        return new Response("congé update success", Response::HTTP_OK);


    }


    #[Route('/{id}', name: 'app_conge_delete_mobile', methods: ['POST'])]
    public function delete(Request $request, Conge $conge, CongeRepository $congeRepository, EmployeRepository $employeRepository, EntityManagerInterface $entityManager): Response
    {
        $employe = $conge->getEmploye();

        $congeRepository->remove($conge, true);

        // Update employe etat
        $conges = $congeRepository->findByDateDebutAndDateFin(new \DateTime());
        $etat = 'Disponible';

        foreach ($conges as $conge) {
            if ($conge->getEmploye() === $employe) {
                $etat = 'Congé';
                break; // exit the loop once a matching conge is found
            }
        }

        $employe->setEtat($etat);
        $employeRepository->save($employe);
        $entityManager->flush();

        return new Response("congé suprimer avec success", Response::HTTP_OK);
    }

}
