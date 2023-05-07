<?php

namespace App\Repository;

use App\Entity\Exercices;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;


/**
 * @extends ServiceEntityRepository<Exercices>
 *
 * @method Exercices|null find($id, $lockMode = null, $lockVersion = null)
 * @method Exercices|null findOneBy(array $criteria, array $orderBy = null)
 * @method Exercices[]    findAll()
 * @method Exercices[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class ExercicesRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Exercices::class);
    }

    public function save(Exercices $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function remove(Exercices $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }
    public function findAllSortedByName()
    {
        return $this->createQueryBuilder('e')
            ->orderBy('e.nom', 'ASC')
            ->getQuery()
            ->getResult();
    }
    public function findByNom($nom)
    {
        return $this->createQueryBuilder('e')
            ->where('e.nom = :nom')
            ->setParameter('nom', $nom)
            ->getQuery()
            ->getResult()
            ;
    }
//    /**
//     * @return Exercices[] Returns an array of Exercices objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('e')
//            ->andWhere('e.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('e.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?Exercices
//    {
//        return $this->createQueryBuilder('e')
//            ->andWhere('e.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
