<?php

namespace App\Entity;

use App\Repository\LivraisonRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Serializer\Annotation\Groups;
use Doctrine\DBAL\Types\Types;


#[ORM\Entity(repositoryClass: LivraisonRepository::class)]
class Livraison
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    #[Groups("post:read")]
    private ?int $id;

    #[ORM\Column(length: 255)]
    #[Groups("post:read")]
    private ?string $adresse;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    #[Groups("post:read")]
    private ?\DateTimeInterface $date;

    #[ORM\OneToOne(inversedBy: 'livraison')]
    #[ORM\JoinColumn(name: "commande", referencedColumnName: "id_c")]
    private Commande $commande;


    #[ORM\Column]
    #[Groups("post:read")]
    private ?bool $confirmer = false;

    #[ORM\Column(length: 255)]
    #[Groups("post:read")]
    #[Assert\NotBlank(message: "ce chanp est obligatoire")]
    #[Assert\Email(message: "valid email")]
    private $email;

    #[ORM\Column]
    private ?float $prix = 7;


    //ctor
    public function __construct()
    {
    }


    public function getId(): ?int
    {
        return $this->id;
    }

    public function getAdresse(): ?string
    {
        return $this->adresse;
    }

    public function setAdresse(string $adresse): self
    {
        $this->adresse = $adresse;

        return $this;
    }

    public function getDate(): ?\DateTimeInterface
    {
        return $this->date;
    }

    public function setDate(\DateTimeInterface $date): self
    {
        $this->date = $date;

        return $this;
    }

    public function getCommande(): ?Commande
    {
        return $this->commande;
    }

    public function setCommande(?Commande $commande): self
    {
        $this->commande = $commande;

        return $this;
    }

    public function isConfirmer(): ?bool
    {
        return $this->confirmer;
    }

    public function setConfirmer(bool $confirmer): self
    {
        $this->confirmer = $confirmer;

        return $this;
    }

    public function getEmail(): ?string
    {
        return $this->email;
    }

    public function setEmail(string $email): self
    {
        $this->email = $email;

        return $this;
    }

    public function getPrix(): ?float
    {
        return $this->prix;
    }

    public function setPrix(float $prix): self
    {
        $this->prix = $prix;

        return $this;
    }


}
