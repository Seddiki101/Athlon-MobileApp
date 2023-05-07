<?php

namespace App\Repository;

use App\Entity\Employe;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Employe>
 *
 * @method Employe|null find($id, $lockMode = null, $lockVersion = null)
 * @method Employe|null findOneBy(array $criteria, array $orderBy = null)
 * @method Employe[]    findAll()
 * @method Employe[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class EmployeRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Employe::class);
    }

    public function save(Employe $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function remove(Employe $entity, bool $flush = false): void
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
    public function findAllSortedByName2()
    {
        return $this->createQueryBuilder('e')
            ->orderBy('e.prenom', 'ASC')
            ->getQuery()
            ->getResult();
    }
    public function findAllSortedBySalaire()
    {
        return $this->createQueryBuilder('e')
            ->orderBy('e.salaire', 'ASC')
            ->getQuery()
            ->getResult();
    }
    public function findByCin($cin)
    {
        return $this->createQueryBuilder('e')
            ->where('e.cin = :cin')
            ->setParameter('cin', $cin)
            ->getQuery()
            ->getResult()
            ;
    }
    public function countBySalaryGreaterThan($amount)
{
    $qb = $this->createQueryBuilder('e');
    $qb->select('COUNT(e.id)')
       ->where('e.salaire > :amount')
       ->setParameter('amount', $amount);

    return $qb->getQuery()->getSingleScalarResult();
}
public function countBySalaryLessThanOrEqual($amount)
{
    $qb = $this->createQueryBuilder('e');
    $qb->select('COUNT(e.id)')
       ->where('e.salaire <= :amount')
       ->setParameter('amount', $amount);

    return $qb->getQuery()->getSingleScalarResult();
}
//    /**
//     * @return Employe[] Returns an array of Employe objects
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

//    public function findOneBySomeField($value): ?Employe
//    {
//        return $this->createQueryBuilder('e')
//            ->andWhere('e.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
