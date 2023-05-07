<?php

namespace App\Controller;
//namespace App\Service;

use App\Entity\Cours;
use App\Form\CoursType;
use App\Repository\CoursRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;


#[Route('/mobile/cours')]
class CoursControllerMobile extends AbstractController
{
    #[Route('/', name: 'app_cours_index_mobile', methods: ['GET'])]
    public function index(CoursRepository $coursRepository, NormalizerInterface $normalizable): Response
    {
        $jsonContent = $normalizable->normalize($coursRepository->findAll(), 'json', ['groups' => 'extended']);

        return new \Symfony\Component\HttpFoundation\JsonResponse($jsonContent);

    }

    #[Route('/new', name: 'app_cours_new_mobile', methods: ['GET', 'POST'])]
    public function new(Request $request, CoursRepository $coursRepository, MailerInterface $mailer): Response
    {
        $cour = new Cours();
        $cour->setDescriptionCours($request->get('description_cours'));
        $cour->setNom($request->get('nom'));
        $cour->setNiveauCours($request->get('Niveau_cours'));
        $cour->setDureeCours($request->get('duree_cours'));
        $cour->setCapacity($request->get('capacity'));
        $cour->setImageCours($request->get('image_cours'));
        $html = "un cour ajouter <br> description:  " . $cour->getDescriptionCours() . "<br> nom: " . $cour->getNom() . "<br> niveau: " . $cour->getNiveauCours() . "<br> duree: " . $cour->getDureeCours();

        $email = (new Email())
            ->from('from@example.com')
            ->to("to@example.com")
            ->subject('Cour Ajouter')
            ->html($html);

        $mailer->send($email);
        $coursRepository->save($cour, true);
        return new Response('cour ajout success', Response::HTTP_OK);

    }

    #[Route('/{id}', name: 'app_cours_show', methods: ['GET'])]
    public function show(Cours $cour): Response
    {
        return $this->render('cours/show.html.twig', [
            'cour' => $cour,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_cours_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Cours $cour, CoursRepository $coursRepository): Response
    {
        $form = $this->createForm(CoursType::class, $cour);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            $file = $form->get('image_cours')->getData();

            if ($file) {
                $fileName = uniqid() . '.' . $file->guessExtension();
                $file->move(
                    $this->getParameter('media'),
                    $fileName
                );
                $cour->setImageCours($fileName);
            }
            $coursRepository->save($cour, true);

            return $this->redirectToRoute('app_cours_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('cours/edit.html.twig', [
            'cour' => $cour,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_cours_delete', methods: ['POST'])]
    public function delete(Request $request, Cours $cour, CoursRepository $coursRepository): Response
    {
        $coursRepository->remove($cour, true);


        return new Response('cour suprimé avec success', Response::HTTP_OK);
    }

}
