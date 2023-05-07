<?php

namespace App\Entity;
use App\Entity\Livraison;

use App\Repository\CommandeRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\DBAL\Types\Types;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Serializer\Annotation\Groups;


#[ORM\Entity(repositoryClass: CommandeRepository::class)]
class Commande
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    #[Groups("post:read")]
    private ?int $idC;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    #[Groups("post:read")]
    #[Assert\NotBlank(message: "ce chanp est obligatoire")]
    private ?\DateTimeInterface $date;


    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message: "ce chanp est obligatoire")]
    #[Groups("post:read")]
    private ?string $user;


    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message: "ce chanp est obligatoire")]
    #[Groups("post:read")]
    private ?string $statue = 'pending';


    #[ORM\OneToOne(mappedBy: 'commande', targetEntity: Livraison::class)]
    #[Groups("post:read")]
    private Livraison $livraison;

    #[ORM\Column]
    #[Groups("post:read")]
    private ?float $remise = 0;

    #[ORM\OneToMany(mappedBy: 'commande', targetEntity: CommandeItem::class)]
    private Collection $orderItem;

    public function __construct()
    {
        $this->orderItem = new ArrayCollection();
    }

    public function getIdC(): ?int
    {
        return $this->idC;
    }

    public function setIdC(int $idC): self
    {
        $this->idC = $idC;

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

    public function getUser(): ?string
    {
        return $this->user;
    }

    public function setUser(string $user): self
    {
        $this->user = $user;

        return $this;
    }

    public function getStatue(): ?string
    {
        return $this->statue;
    }

    public function setStatue(string $statue): self
    {
        $this->statue = $statue;

        return $this;
    }


//


    public function getLivraison(): ?Livraison
    {
        return $this->livraison;
    }

    public function setLivraison(?Livraison $livraison): self
    {
        // unset the owning side of the relation if necessary
        if ($livraison === null && $this->livraison !== null) {
            $this->livraison->setCommande(null);
        }

        // set the owning side of the relation if necessary
        if ($livraison !== null && $livraison->getCommande() !== $this) {
            $livraison->setCommande($this);
        }

        $this->livraison = $livraison;

        return $this;
    }

    public function getRemise(): ?float
    {
        return $this->remise;
    }

    public function setRemise(float $remise): self
    {
        $this->remise = $remise;

        return $this;
    }

    public function __toString()
    {
        return $this->getIdC() . "";
    }

    /**
     * @return Collection<int, CommandeItem>
     */
    public function getOrderItem(): Collection
    {
        return $this->orderItem;
    }

    public function addOrderItem(CommandeItem $orderItem): self
    {
        if (!$this->orderItem->contains($orderItem)) {
            $this->orderItem->add($orderItem);
            $orderItem->setCommande($this);
        }

        return $this;
    }

    public function removeOrderItem(CommandeItem $orderItem): self
    {
        if ($this->orderItem->removeElement($orderItem)) {
            // set the owning side to null (unless already changed)
            if ($orderItem->getCommande() === $this) {
                $orderItem->setCommande(null);
            }
        }

        return $this;
    }


}
