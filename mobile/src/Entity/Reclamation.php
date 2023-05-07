<?php

namespace App\Entity;

use App\Repository\ReclamationRepository;
use Doctrine\ORM\Mapping as ORM;

use Symfony\Component\Validator\Constraints as Assert;


use Symfony\Component\Serializer\Annotation\Groups;

#[ORM\Entity(repositoryClass: ReclamationRepository::class)]
class Reclamation
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
	#[Groups("reclamations")]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
	#[Assert\NotBlank(message : "title is required")]
	#[Groups("reclamations")]
    private ?string $titre = null;

    #[ORM\Column(length: 255)]
	#[Assert\NotBlank(message : "description is required")]
	#[Groups("reclamations")]
    private ?string $desipticon = null;

    #[ORM\ManyToOne(inversedBy: 'reclamationX')]
    private ?User $userX = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getTitre(): ?string
    {
        return $this->titre;
    }

    public function setTitre(string $titre): self
    {
        $this->titre = $titre;

        return $this;
    }

    public function getDesipticon(): ?string
    {
        return $this->desipticon;
    }

    public function setDesipticon(string $desipticon): self
    {
        $this->desipticon = $desipticon;

        return $this;
    }

    public function getUserX(): ?User
    {
        return $this->userX;
    }

    public function setUserX(?User $userX): self
    {
        $this->userX = $userX;

        return $this;
    }
}
