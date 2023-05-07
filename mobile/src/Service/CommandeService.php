<?php

namespace App\Service;

class CommandeService
{
    public static function insertOrUpdate(\App\Entity\Commande $commande, \Doctrine\ORM\EntityManagerInterface $entityManager)
    {

        $dbCommande = $entityManager->find(\App\Entity\Commande::class, $commande->getIdC());
        if (!$dbCommande) {
            $dbCommande = $commande;
        } else
            $dbCommande->setStatue($commande->getStatue());

        $entityManager->persist($dbCommande);
        $entityManager->flush();
    }

    public function placeOrder(\App\Entity\Commande $commande, CartService1 $cartService, \Doctrine\ORM\EntityManagerInterface $entityManager)
    {

        $commande->setStatue("placed");
        if ($cartService->getTotal() > 100) {
            $commande->setRemise(10);
        }
        self::insertOrUpdate($commande, $entityManager);
        $cartService->clear();
        $newCommande = new \App\Entity\Commande();
        $newCommande->setUser($commande->getUser());
        $date = new \DateTime();
//        $date->setDate(2022, 10, 28);
        $newCommande->setDate($date);

        $entityManager->persist($newCommande);
        $entityManager->flush();

        return $commande;

    }
}