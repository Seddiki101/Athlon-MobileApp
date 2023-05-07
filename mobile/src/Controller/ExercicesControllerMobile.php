<?php

namespace App\Controller;
//namespace App\Service;

use App\Entity\Exercices;

use App\Form\ExercicesType;
use App\Repository\ExercicesRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;


#[Route('/mobile/exercices')]
class ExercicesControllerMobile extends AbstractController
{
    #[Route('/', name: 'app_exercices_index_mobile', methods: ['GET'])]
    public function index(ExercicesRepository $exercicesRepository, NormalizerInterface $normalizable): Response
    {
        $jsonContent = $normalizable->normalize($exercicesRepository->findAll(), 'json', ['groups' => 'extended']);

        return new \Symfony\Component\HttpFoundation\JsonResponse($jsonContent);
    }

    #[Route('/new', name: 'app_exercices_new_mobile', methods: ['GET', 'POST'])]
    public function new(Request $request, ExercicesRepository $exercicesRepository, \App\Repository\CoursRepository $coursRepository): Response
    {
        $exercice = new Exercices();
        $exercice->setNom($request->get('nom'));
        $exercice->setDureeExercices($request->get('duree_exercices'));
        $exercice->setNombreRepetitions($request->get('nombre_repetitions'));
        $exercice->setDescExercices($request->get('desc_exercices'));
        $exercice->setMachine($request->get('machine'));
        $exercice->setImageExercice($request->get('image_exercice'));
        $exercice->setCours($coursRepository->findOneById($request->get('cour')));
        $exercicesRepository->save($exercice, true);

        return new Response('exercice ajout success', Response::HTTP_OK);


    }

    #[Route('/{id}', name: 'app_exercices_show', methods: ['GET'])]
    public function show(Exercices $exercice): Response
    {
        return $this->render('exercices/show.html.twig', [
            'exercice' => $exercice,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_exercices_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Exercices $exercice, ExercicesRepository $exercicesRepository): Response
    {
        $form = $this->createForm(ExercicesType::class, $exercice);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $exercicesRepository->save($exercice, true);

            return $this->redirectToRoute('app_exercices_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('exercices/edit.html.twig', [
            'exercice' => $exercice,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_exercices_delete_mobile', methods: ['POST'])]
    public function delete(Request $request, Exercices $exercice, ExercicesRepository $exercicesRepository): Response
    {
        $exercicesRepository->remove($exercice, true);
        return new Response('exercice supreimée avec success', Response::HTTP_OK);
    }
}
