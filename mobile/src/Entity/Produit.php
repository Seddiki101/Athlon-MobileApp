<?php

namespace App\Entity;

use App\Repository\ProduitRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Serializer\Annotation\Groups;

#[ORM\Entity(repositoryClass: ProduitRepository::class)]
class Produit
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    #[Groups("Produit")]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message:"veuillez remplir le champs")]
    #[Groups("Produit")]
    private ?string $brand = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message:"veuillez remplir le champs")]
    #[Assert\Length(min:7,max:100, minMessage:"La descreption doit etre > 7.", maxMessage:"Doit etre <=100")]
    #[Groups("Produit")]
    private ?string $description = null;


    
    #[ORM\Column]
    #[Assert\NotBlank(message:"veuillez remplir le champs")]
    #[Assert\NotEqualTo(
        value: 0,
    )]
    #[Assert\PositiveOrZero(message:"Le prix doit être un nombre positif.")]
    #[Assert\Range(
          min : 0,
         max : 99999999.99,
         
    )]
    #[Groups("Produit")]
    private ?float $prix = null;

    #[ORM\Column(length: 255)]
    #[Groups("Produit")]
    private ?string $image = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message:"veuillez remplir le champs")]
    #[Assert\Length(min:3, minMessage:"Votre nom inferieure a 3 caractères.")]
    #[Assert\Regex(
        pattern:"/^[^0-9]+$/",
        message:"Le nom ne doit pas contenir des chiffres"
    )]
    #[Groups("Produit")]
    
    private ?string $nom = null;

    #[ORM\ManyToOne(inversedBy: 'produits')]
    #[Assert\NotBlank(message:"veuillez remplir ce champs")]
    #[Groups("Produit")]
    private ?Categorie $Categories = null;

    #[ORM\OneToMany(mappedBy: 'produits', targetEntity: Comments::class, orphanRemoval: true)]
     private Collection $comments;

    #[ORM\Column(nullable: true)]
    private ?int $likes = null;

    #[ORM\Column(nullable: true)]
    private ?int $dislikes = null;

    public function __construct()
    {
        $this->comments = new ArrayCollection();
    }

    


    public function getId(): ?int
    {
        return $this->id;
    }

    public function getBrand(): ?string
    {
        return $this->brand;
    }

    public function setBrand(string $brand): self
    {
        $this->brand = $brand;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): self
    {
        $this->description = $description;

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

    public function getImage(): ?string
    {
        return $this->image;
    }

    public function setImage(string $image): self
    {
        $this->image = $image;

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

    public function getCategories(): ?Categorie
    {
        return $this->Categories;
    }

    public function setCategories(?Categorie $Categories): self
    {
        $this->Categories = $Categories;

        return $this;
    }

    public function __toString() {
        return $this->nom;
    }

   

    /**
     * @return Collection<int, Comments>
     */
    public function getComments(): Collection
    {
        return $this->comments;
    }

    public function addComment(Comments $comment): self
    {
        if (!$this->comments->contains($comment)) {
            $this->comments->add($comment);
            $comment->setProduits($this);
        }

        return $this;
    }

    public function removeComment(Comments $comment): self
    {
        if ($this->comments->removeElement($comment)) {
            // set the owning side to null (unless already changed)
            if ($comment->getProduits() === $this) {
                $comment->setProduits(null);
            }
        }

        return $this;
    }

    public function getLikes(): ?int
    {
        return $this->likes;
    }

    public function setLikes(?int $likes): self
    {
        $this->likes = $likes;

        return $this;
    }

    public function getDislikes(): ?int
    {
        return $this->dislikes;
    }

    public function setDislikes(?int $dislikes): self
    {
        $this->dislikes = $dislikes;

        return $this;
    }

}