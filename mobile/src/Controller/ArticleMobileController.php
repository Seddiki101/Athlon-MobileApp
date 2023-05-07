<?php

namespace App\Controller;
//namespace App\Service;

use App\Entity\Article;
use App\Repository\ArticleRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;


#[Route('/mobile/article')]
class ArticleMobileController extends AbstractController
{
    #[Route('/', name: 'app_article_index_mobile', methods: ['GET'])]
    public function index(Request $request, ArticleRepository $articleRepository, NormalizerInterface $normalizable): Response
    {
        $articles = $articleRepository->findAll();
        $jsonContent = $normalizable->normalize($articles, 'json', ['groups' => ['article', 'sujet']]);
        return new \Symfony\Component\HttpFoundation\JsonResponse($jsonContent);
    }


//    #[Route('/recherche', name: 'app_article_recherche', methods: ['GET'])]
//    public function index4(Request $request, ArticleRepository $articleRepository): Response
//    {
//        $query = $request->query->get('q');
//
//        $results = $articleRepository->findByAuteur($query); // Remplacez "searchByTitle" par la méthode que vous utilisez pour rechercher les cours
//
//        return $this->render('article/index2.html.twig', [
//            'articles' => $results,
//            'query' => $query,
//
//        ]);
//    }
//
//    #[Route('/tri_titre', name: 'app_article_tri_titre', methods: ['GET'])]
//    public function index2(Request $request, ArticleRepository $articleRepository): Response
//    {
//        $searchTerm = $request->query->get('search');
//        $articles = $articleRepository->findByTitle($searchTerm);
//
//        return $this->render('article/index2.html.twig', [
//            'articles' => $articles,
//        ]);
//
//
//    }
    #[Route('/new', name: 'app_article_new_mobile', methods: ['GET', 'POST'])]
    public function new(Request $request,  MailerInterface $mailer,ArticleRepository $articleRepository, \App\Repository\SujetRepository $sujetRepository): Response
    {
        $article = new Article();

        $article->setAuteur($request->get("auteur"));
        $article->setDescripton($request->get("description"));
        $article->setSujetX($sujetRepository->findOneById($request->get("sujetId")));
        $article->setTitre($request->get("titre"));
        $article->setImgArticle($request->get("img"));
        $html = "un article ajouter <br> auteur:  " . $article->getAuteur() . "<br> description: " . $article->getDescripton();

        $email = (new Email())
            ->from('from@example.com')
            ->to("to@example.com")
            ->subject('Article Ajouter')
            ->html($html);

        $mailer->send($email);
        $articleRepository->save($article, true);

        return new \Symfony\Component\HttpFoundation\JsonResponse("article ajoute avec success");
    }

    #[Route('/{id}/edit', name: 'app_article_edit_mobile', methods: ['GET', 'POST'])]
    public function edit($id, Request $request, ArticleRepository $articleRepository, \App\Repository\SujetRepository $sujetRepository): Response
    {
        $article = new Article();
        $article->setid($id);
        $article->setAuteur($request->get("auteur"));
        $article->setDescripton($request->get("description"));
        $article->setSujetX($sujetRepository->findOneById($request->get("sujetId")));
        $article->setTitre($request->get("titre"));
        if ($request->get("img") != null) {
            $article->setImgArticle($request->get("img"));
        }


        $articleRepository->save($article, true);

        return new \Symfony\Component\HttpFoundation\JsonResponse("article ajoute avec success");
    }

    #[Route('/{id}', name: 'app_article_delete_mobile', methods: ['POST'])]
    public function delete(Request $request, Article $article, ArticleRepository $articleRepository): Response
    {
        $articleRepository->remove($article, true);

        return new \Symfony\Component\HttpFoundation\JsonResponse("article supprime avec success");
    }

    #[Route('/stats', name: 'app_article_stats_mobile', methods: ['GET'])]
    public function stats(ArticleRepository $articleRepository, NormalizerInterface $normalizable): Response
    {
        $stats = $articleRepository->getAuthorStats();
        $jsonContent = $normalizable->normalize($stats, 'json');
        return new \Symfony\Component\HttpFoundation\JsonResponse($jsonContent);
    }


    #[Route("/like/{id}/{type}", name: "app_testfront_like_mobile")]
    public function like(Request $request, Article $article, $type)
    {
        if ($type == 'like') {
            $article->setLikes($article->getLikes() + 1);
        } else {
            $article->setDislikes($article->getDislikes() + 1);
        }

        $this->getDoctrine()->getManager()->flush();
        return new \Symfony\Component\HttpFoundation\JsonResponse("success");
    }

}