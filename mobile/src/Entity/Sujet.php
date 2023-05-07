<?php

namespace App\Entity;

use App\Repository\SujetRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: SujetRepository::class)]
class Sujet
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    private ?string $nom = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message:"La description de l'exercice est obligatoire")]
    #[Assert\Length(
        max :15,
        maxMessage : "La description d exercice ne doit pas dépasser {{ limit }} caractères")]
    private string $descr ;


    #[ORM\Column(length: 255, nullable: true)]
    private ?string $imgSujet = null;

    #[ORM\OneToMany(mappedBy: 'SujetX', targetEntity: Article::class)]
    private Collection $articleX;

    public function __construct()
    {
        $this->articleX = new ArrayCollection();
    }

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

    public function getDescr(): ?string
    {
        return $this->descr;
    }

    public function setDescr(string $descr): self
    {
        $this->descr = $descr;

        return $this;
    }

    public function getImgSujet(): ?string
    {
        return $this->imgSujet;
    }

    public function setImgSujet(?string $imgSujet): self
    {
        $this->imgSujet = $imgSujet;

        return $this;
    }

    /**
     * @return Collection<int, Article>
     */
    public function getArticleX(): Collection
    {
        return $this->articleX;
    }

    public function addArticleX(Article $articleX): self
    {
        if (!$this->articleX->contains($articleX)) {
            $this->articleX->add($articleX);
            $articleX->setSujetX($this);
        }

        return $this;
    }

    public function removeArticleX(Article $articleX): self
    {
        if ($this->articleX->removeElement($articleX)) {
            // set the owning side to null (unless already changed)
            if ($articleX->getSujetX() === $this) {
                $articleX->setSujetX(null);
            }
        }

        return $this;
    }
	
	
	
	
	
	
	public function __toString(): string
	{
   return ("Sujet ".$this.getNom() );
	}
	
	
}
