<?php

namespace App\Entity;

use App\Repository\EmployeRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Symfony\Component\Validator\Constraints as Assert;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Serializer\Annotation\Groups;


#[ORM\Entity(repositoryClass: EmployeRepository::class)]
class Employe
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    #[Groups("employes")]
    private ?int $id = null;

    #[ORM\Column]
    #[Assert\NotBlank(message:"CIN ne doit pas être vide")]
    #[Groups("employes")]
    private ?int $cin = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message:"Nom ne doit pas être vide")]
    #[Assert\Length(min:3, max:255, minMessage:"Entrer un nom d'au moins {{ limit }} caractères", maxMessage:"Nom ne doit pas dépasser {{ limit }} caractères")]
    #[Assert\Regex(pattern: '/^[a-zA-Z]+$/i', message: "Le nom doit contenir uniquement des caractères alphabétiques")]
    #[Groups("employes")]
    private ?string $nom = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message:"Prenom ne doit pas être vide")]
    #[Assert\Regex(pattern: '/^[a-zA-Z]+$/i', message: "Le Prenom doit contenir uniquement des caractères alphabétiques")]
    #[Assert\Length(min:3, max:255, minMessage:"Entrer un Prenom d'au moins {{ limit }} caractères", maxMessage:"Nom ne doit pas dépasser {{ limit }} caractères")]
    #[Groups("employes")]
    private ?string $prenom = null;

    #[ORM\Column(length: 255, nullable: true)]
    #[Groups("employes")]
    private ?string $img_employe = null;

    #[ORM\OneToMany(mappedBy: 'employe', targetEntity: Conge::class)]
    private Collection $conges;

    #[ORM\Column(length: 255)]
    #[Groups("employes")]
    private ?string $etat = null;

    #[ORM\Column]
    #[Groups("employes")]
    private ?float $salaire = null;

    public function __construct()
    {
        $this->conges = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getCin(): ?int
    {
        return $this->cin;
    }

    public function setCin(int $cin): self
    {
        $this->cin = $cin;

        return $this;
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

    public function getPrenom(): ?string
    {
        return $this->prenom;
    }

    public function setPrenom(string $prenom): self
    {
        $this->prenom = $prenom;

        return $this;
    }

    public function getImgEmploye(): ?string
    {
        return $this->img_employe;
    }

    public function setImgEmploye(?string $img_employe): self
    {
        $this->img_employe = $img_employe;

        return $this;
    }

    /**
     * @return Collection<int, Conge>
     */
    public function getConges(): Collection
    {
        return $this->conges;
    }

    public function addConge(Conge $conge): self
    {
        if (!$this->conges->contains($conge)) {
            $this->conges->add($conge);
            $conge->setEmploye($this);
        }

        return $this;
    }

    public function removeConge(Conge $conge): self
    {
        if ($this->conges->removeElement($conge)) {
            // set the owning side to null (unless already changed)
            if ($conge->getEmploye() === $this) {
                $conge->setEmploye(null);
            }
        }

        return $this;
    }














    public function __toString(): string
    {
        return ("Employe".$this.getNom()."_".$this.getPrenom() );
    }

    public function getEtat(): ?string
    {
        return $this->etat;
    }

    public function setEtat(string $etat): self
    {
        $this->etat = $etat;

        return $this;
    }

    public function getSalaire(): ?float
    {
        return $this->salaire;
    }

    public function setSalaire(float $salaire): self
    {
        $this->salaire = $salaire;

        return $this;
    }




}
