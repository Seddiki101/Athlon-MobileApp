<?php

namespace App\Entity;

use App\Repository\CongeRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Validator\Context\ExecutionContextInterface;
use Symfony\Component\Serializer\Annotation\Groups;


#[ORM\Entity(repositoryClass: CongeRepository::class)]
class Conge
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    #[Groups("conge")]
    private ?int $id = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    #[Groups("conge")]
    private ?\DateTimeInterface $dateD = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    #[Groups("conge")]
    private ?\DateTimeInterface $dateF = null;

    #[ORM\Column(length: 255)]
    #[Groups("conge")]
    private ?string $type = null;

    #[ORM\ManyToOne(inversedBy: 'conges')]
     #[Groups("conge")]
    private ?Employe $employe = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getDateD(): ?\DateTimeInterface
    {
        return $this->dateD;
    }

    public function setDateD(\DateTimeInterface $dateD): self
    {
        $this->dateD = $dateD;

        return $this;
    }

    public function getDateF(): ?\DateTimeInterface
    {
        return $this->dateF;
    }

    public function setDateF(\DateTimeInterface $dateF): self
    {
        $this->dateF = $dateF;

        return $this;
    }

    public function getType(): ?string
    {
        return $this->type;
    }

    public function setType(string $type): self
    {
        $this->type = $type;

        return $this;
    }

    public function getEmploye(): ?employe
    {
        return $this->employe;
    }

    public function setEmploye(?employe $employe): self
    {
        $this->employe = $employe;

        return $this;
    }
    #[Assert\Callback]
    public function validate(ExecutionContextInterface $context): void
    {
        if ($this->dateD > $this->dateF) {
            $context->buildViolation('La date de début doit être antérieure à la date de fin.')
                ->atPath('dateD')
                ->addViolation();

            $context->buildViolation('La date de fin doit être postérieure à la date de début.')
                ->atPath('dateF')
                ->addViolation();
        }
    }
}
