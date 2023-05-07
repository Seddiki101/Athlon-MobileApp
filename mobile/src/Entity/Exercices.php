<?php

namespace App\Entity;

use App\Repository\CoursRepository;
use App\Repository\ExercicesRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: ExercicesRepository::class)]
class Exercices
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message:"Le nom d exercice est obligatoire")]
    #[Assert\Length(
        max :15,
        maxMessage : "Le nom d exercice ne doit pas dépasser {{ limit }} caractères")]
    #[Assert\Regex(
        pattern: "/^[a-zA-Z0-9_ ]+$/",
        message: "Le nom d exercice ne doit contenir que des lettres, des chiffres, des espaces ou des underscores")]
    private $nom;
    #[ORM\Column]
    #[Assert\NotBlank(message:"La durée est obligatoire")]
    #[Assert\Positive(message:"La durée doit être positive")]
    #[Assert\LessThanOrEqual(value:60, message:"La durée ne doit pas dépasser 60 minutes")]
    private $duree_exercices;


    #[ORM\Column]
    #[Assert\NotBlank(message:"Le nombre de repetitions est obligatoire")]
      #[Assert\Positive(message:"Le nombre de repetitions doit être positive")]
    #[Assert\LessThanOrEqual(value:20, message:"La nombre de repetitions ne doit pas dépasser 20fois")]
    private $nombre_repetitions;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message:"La description de l'exercice est obligatoire")]
    #[Assert\Length(
        max :15,
        maxMessage : "La description d exercice ne doit pas dépasser {{ limit }} caractères")]
    private string $desc_exercices ;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message:"Le nom de machine de l'exercice est obligatoire")]
    #[Assert\Length(
        max :15,
        maxMessage : "Le nom de machine ne doit pas dépasser {{ limit }} caractères")]
    private string $machine ;

    #[ORM\ManyToOne(inversedBy: 'exercices')]
    private ?Cours $Cours = null;

    #[ORM\Column(length: 255, nullable: true)]
    private ?string $image_exercice = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function setNom(string $nom): self
    {
        $this->nom = $nom;

        return $this;
    }

    public function getDureeExercices(): ?int
    {
        return $this->duree_exercices;
    }

    public function setDureeExercices(int $duree_exercices): self
    {
        $this->duree_exercices = $duree_exercices;

        return $this;
    }

    public function getNombreRepetitions(): ?int
    {
        return $this->nombre_repetitions;
    }

    public function setNombreRepetitions(int $nombre_repetitions): self
    {
        $this->nombre_repetitions = $nombre_repetitions;

        return $this;
    }

    public function getDescExercices(): ?string
    {
        return $this->desc_exercices;
    }

    public function setDescExercices(string $desc_exercices): self
    {
        $this->desc_exercices = $desc_exercices;

        return $this;
    }

    public function getMachine(): ?string
    {
        return $this->machine;
    }

    public function setMachine(string $machine): self
    {
        $this->machine = $machine;

        return $this;
    }

    public function getCours(): ?Cours
    {
        return $this->Cours;
    }

    public function setCours(?Cours $Cours): self
    {
        $this->Cours = $Cours;

        return $this;
    }

    public function getImageExercice(): ?string
    {
        return $this->image_exercice;
    }

    public function setImageExercice(?string $image_exercice): self
    {
        $this->image_exercice = $image_exercice;

        return $this;
    }
}
