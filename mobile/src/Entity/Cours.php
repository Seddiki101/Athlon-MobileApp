<?php

namespace App\Entity;

use App\Repository\CoursRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;


#[ORM\Entity(repositoryClass: CoursRepository::class)]
class Cours
{


    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
   #[Assert\NotBlank(message:"Le nom du cours est obligatoire")]
   #[Assert\Length(
    max :15,
    maxMessage : "Le nom du cours ne doit pas dépasser {{ limit }} caractères")]
    #[Assert\Regex(
     pattern: "/^[a-zA-Z0-9_ ]+$/",
     message: "Le nom du cours ne doit contenir que des lettres, des chiffres, des espaces ou des underscores")]
    private $Nom;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message:"La description de cours est obligatoire")]
    #[Assert\Length(
        max :15,
        maxMessage : "La description de cours ne doit pas dépasser {{ limit }} caractères")]
    private string $description_cours ;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message:"Le niveau de cours est obligatoire")]
    private string $Niveau_cours ;

     #[ORM\Column]
     #[Assert\NotBlank(message:"La durée est obligatoire")]
     #[Assert\Positive(message:"La durée doit être positive")]
     #[Assert\LessThanOrEqual(value:90, message:"La durée ne doit pas dépasser 90 minutes")]
    private $duree_cours;
    #[ORM\Column]
    #[Assert\NotBlank(message:"La capacite est obligatoire")]
    #[Assert\Positive(message:"La capacite doit être positive")]
    #[Assert\LessThanOrEqual(value:50, message:"La capacite ne doit pas dépasser 50 personnes")]
    private $capacity;



    #[ORM\OneToMany(mappedBy: 'Cours', targetEntity: Exercices::class)]
    private Collection $exercices;

    #[ORM\Column(length: 255)]
    private ?string $image_cours = null;

    public function __construct()
    {
        $this->exercices = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNom(): ?string
    {
        return $this->Nom;
    }

    public function setNom(string $Nom): self
    {
        $this->Nom = $Nom;

        return $this;
    }

    public function getDescriptionCours(): ?string
    {
        return $this->description_cours;
    }

    public function setDescriptionCours(string $description_cours): self
    {
        $this->description_cours = $description_cours;

        return $this;
    }

    public function getNiveauCours(): ?string
    {
        return $this->Niveau_cours;
    }

    public function setNiveauCours(string $Niveau_cours): self
    {
        $this->Niveau_cours = $Niveau_cours;

        return $this;
    }

    public function getCapacity(): ?int
    {
        return $this->capacity;
    }

    public function setCapacity(int $capacity): self
    {
        $this->capacity = $capacity;

        return $this;
    }

    public function getDureeCours(): ?int
    {
        return $this->duree_cours;
    }

    public function setDureeCours(int $duree_cours): self
    {
        $this->duree_cours = $duree_cours;

        return $this;
    }

    /**
     * @return Collection<int, Exercices>
     */
    public function getExercices(): Collection
    {
        return $this->exercices;
    }

    public function addExercice(Exercices $exercice): self
    {
        if (!$this->exercices->contains($exercice)) {
            $this->exercices->add($exercice);
            $exercice->setCours($this);
        }

        return $this;
    }

    public function removeExercice(Exercices $exercice): self
    {
        if ($this->exercices->removeElement($exercice)) {
            // set the owning side to null (unless already changed)
            if ($exercice->getCours() === $this) {
                $exercice->setCours(null);
            }
        }

        return $this;
    }

    public function getImageCours(): ?string
    {
        return $this->image_cours;
    }

    public function setImageCours(string $image_cours): self
    {
        $this->image_cours = $image_cours;

        return $this;
    }
}
