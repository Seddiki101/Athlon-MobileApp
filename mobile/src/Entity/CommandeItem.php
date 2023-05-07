<?php

namespace App\Entity;

use App\Repository\CommandeItemRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Serializer\Annotation\Groups;


#[ORM\Entity(repositoryClass: CommandeItemRepository::class)]
class CommandeItem
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    #[Groups("post:read")]
    private ?int $id;
//
    /**
     * @ORM\ManyToOne(targetEntity=Produit::class)
     * * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="produit", referencedColumnName="id_p")
     * })
     */
    #[ORM\ManyToOne(targetEntity: Produit::class)]
    #[Assert\NotBlank(message: "ce chanp est obligatoire")]
    #[Groups("post:read")]
    private Produit $produit;

    #[ORM\Column()]
    #[Assert\Positive]
    #[Assert\NotBlank(message: "ce chanp est obligatoire")]
    #[Groups("post:read")]
    private ?int $quantity;

    #[ORM\ManyToOne(inversedBy: 'orderItem')]
    #[ORM\JoinColumn(name: "commande", referencedColumnName: "id_c")]
    private ?Commande $commande = null;


    public function getId(): ?int
    {
        return $this->id;
    }

    public function getProduit(): ?Produit
    {
        return $this->produit;
    }

    public function setProduit(?Produit $produit): self
    {
        $this->produit = $produit;

        return $this;
    }

    public function getQuantity(): ?int
    {
        return $this->quantity;
    }

    public function setQuantity(int $quantity): self
    {
        $this->quantity = $quantity;

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
}
