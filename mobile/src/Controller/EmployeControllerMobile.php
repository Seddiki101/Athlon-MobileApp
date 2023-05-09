<?php

namespace App\Controller;

use App\Entity\Employe;
use App\Form\EmployeType;
use App\Repository\EmployeRepository;
use App\Repository\CongeRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;


#[Route('/mobile/employe')]
class EmployeControllerMobile extends AbstractController
{
    #[Route('/', name: 'app_employe_index_mobile', methods: ['GET'])]
    public function index(EmployeRepository $employeRepository, NormalizerInterface $normalizable): Response
    {
        $nbEmployeesWithSalaryLessOrEqual1000 = $this->getDoctrine()->getRepository(Employe::class)->countBySalaryLessThanOrEqual(1000);

        $result = [
           // 'nbEmployeesWithSalaryGreaterThan1000' => $nbEmployeesWithSalaryGreaterThan1000,
           // 'nbEmployeesWithSalaryLessOrEqual1000' => $nbEmployeesWithSalaryLessOrEqual1000,
            'employes' => $employeRepository->findAll(),
        ];
        $jsonContent = $normalizable->normalize($result, 'json', ['groups' => 'employes']);
        return new \Symfony\Component\HttpFoundation\JsonResponse($jsonContent);
    }

//
//    #[Route('/tri_nom', name: 'app_employe_tri_nom', methods: ['GET'])]
//    public function index2(EmployeRepository $employeRepository): Response
//    {
//        if (!$this->isGranted('ROLE_ADMIN')) {
//            // If not, redirect to a different page or display an error message
//            return $this->redirectToRoute('app_home');
//        }
//
//        $nbEmployeesWithSalaryGreaterThan1000 = $this->getDoctrine()->getRepository(Employe::class)->countBySalaryGreaterThan(1000);
//
//        // Get the number of employees with salary less than or equal to 1000
//        $nbEmployeesWithSalaryLessOrEqual1000 = $this->getDoctrine()->getRepository(Employe::class)->countBySalaryLessThanOrEqual(1000);
//        return $this->render('employe/index2.html.twig', [
//            'nbEmployeesWithSalaryGreaterThan1000' => $nbEmployeesWithSalaryGreaterThan1000,
//            'nbEmployeesWithSalaryLessOrEqual1000' => $nbEmployeesWithSalaryLessOrEqual1000,
//            'employes' => $employeRepository->findAllSortedByName(),
//        ]);
//    }
//
//    #[Route('/tri_prenom', name: 'app_employe_tri_prenom', methods: ['GET'])]
//    public function index3(EmployeRepository $employeRepository): Response
//    {
//        if (!$this->isGranted('ROLE_ADMIN')) {
//            // If not, redirect to a different page or display an error message
//            return $this->redirectToRoute('app_home');
//        }
//        $nbEmployeesWithSalaryGreaterThan1000 = $this->getDoctrine()->getRepository(Employe::class)->countBySalaryGreaterThan(1000);
//
//        // Get the number of employees with salary less than or equal to 1000
//        $nbEmployeesWithSalaryLessOrEqual1000 = $this->getDoctrine()->getRepository(Employe::class)->countBySalaryLessThanOrEqual(1000);
//        return $this->render('employe/index2.html.twig', [
//            'nbEmployeesWithSalaryGreaterThan1000' => $nbEmployeesWithSalaryGreaterThan1000,
//            'nbEmployeesWithSalaryLessOrEqual1000' => $nbEmployeesWithSalaryLessOrEqual1000,
//            'employes' => $employeRepository->findAllSortedByName2(),
//        ]);
//    }
//
//    #[Route('/tri_salaire', name: 'app_employe_tri_salaire', methods: ['GET'])]
//    public function trisalaire(EmployeRepository $employeRepository): Response
//    {
//        if (!$this->isGranted('ROLE_ADMIN')) {
//            // If not, redirect to a different page or display an error message
//            return $this->redirectToRoute('app_home');
//        }
//        $nbEmployeesWithSalaryGreaterThan1000 = $this->getDoctrine()->getRepository(Employe::class)->countBySalaryGreaterThan(1000);
//
//        // Get the number of employees with salary less than or equal to 1000
//        $nbEmployeesWithSalaryLessOrEqual1000 = $this->getDoctrine()->getRepository(Employe::class)->countBySalaryLessThanOrEqual(1000);
//
//        return $this->render('employe/index2.html.twig', [
//            'nbEmployeesWithSalaryGreaterThan1000' => $nbEmployeesWithSalaryGreaterThan1000,
//            'nbEmployeesWithSalaryLessOrEqual1000' => $nbEmployeesWithSalaryLessOrEqual1000,
//            'employes' => $employeRepository->findAllSortedBySalaire(),
//        ]);
//    }
//
//
//    #[Route('/recherche', name: 'app_employe_recherche', methods: ['GET'])]
//    public function index4(Request $request, EmployeRepository $employeRepository): Response
//    {
//        $query = $request->query->get('q');
//
//        $results = $employeRepository->findByCin($query); // Remplacez "searchByTitle" par la méthode que vous utilisez pour rechercher les cours
//
//        return $this->render('employe/index2.html.twig', [
//            'employes' => $results,
//            'query' => $query,
//
//        ]);
//    }


    #[Route('/list', name: 'listemployee')]
    public function list(EmployeRepository $repository, CongeRepository $congeRepository, EntityManagerInterface $entityManager, NormalizerInterface $normalizer)
    {
        $employes = $repository->findAll();


        foreach ($employes as $employe) {
            $conges = $congeRepository->findByDateDebutAndDateFin(new \DateTime());

            if (count($conges) > 0 && $conges[0]->getEmploye() === $employe) {
                $employe->setEtat('Congé');
            } else {
                $employe->setEtat('Disponible');
            }
        }
        $entityManager->flush();

        $jsonContent = $normalizer->normalize($employes, 'json', ['groups' => 'employes']);
        return new \Symfony\Component\HttpFoundation\JsonResponse($jsonContent);
    }


    #[Route('/new', name: 'app_employe_new_mobile', methods: ['GET', 'POST'])]
    public function new(Request $request, EmployeRepository $employeRepository): Response
    {
        $employe = new Employe();


        $employe->setEtat('Disponible');
        $employe->setNom($request->get('nom'));
        $employe->setCin($request->get('cin'));
        $employe->setPrenom($request->get('prenom'));
        $employe->setSalaire($request->get('salaire'));
        $employe->setImgEmploye($request->get("img"));


        $employeRepository->save($employe, true);
        return new Response("employee ajout success", Response::HTTP_OK);


    }


    #[Route('/{id}/edit', name: 'app_employe_edit_mobile', methods: ['GET', 'POST'])]
    public function edit(Request $request, Employe $employe, EmployeRepository $employeRepository): Response
    {
        $employe->setEtat('Disponible');
        $employe->setNom($request->get('nom'));
        $employe->setCin($request->get('cin'));
        $employe->setPrenom($request->get('prenom'));
        $employe->setSalaire($request->get('salaire'));
        $employeRepository->save($employe, true);

        return new Response("employee modif success", Response::HTTP_OK);

    }

    #[Route('/{id}', name: 'app_employe_delete', methods: ['POST'])]
    public function delete(Request $request, Employe $employe, EmployeRepository $employeRepository): Response
    {
        $employeRepository->remove($employe, true);
        return new Response("employee delete success", Response::HTTP_OK);
    }
}
