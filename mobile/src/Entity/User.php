<?php

namespace App\Entity;

use App\Repository\UserRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;
use Symfony\Component\Security\Core\User\PasswordAuthenticatedUserInterface;
use Symfony\Component\Security\Core\User\UserInterface;

use Symfony\Component\Validator\Constraints as Assert;
use SymfonyCasts\Bundle\VerifyEmail\Model\VerifyEmailSignatureComponents;

use Symfony\Component\Serializer\Annotation\Groups;


#[ORM\Entity(repositoryClass: UserRepository::class)]
#[UniqueEntity(fields: ['email'], message: 'There is already an account with this email')]
class User implements UserInterface, PasswordAuthenticatedUserInterface
{
	

	
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
	#[Groups("uzers")]
    private ?int $id = null;

    #[ORM\Column(length: 180, unique: true)]
	#[Groups("uzers")]
	#[Assert\Email(message : "The email '{{ value }}' is not a valid email.",  )]
    private ?string $email = null;

    #[ORM\Column]
	#[Groups("uzers")]
    private array $roles = [];

    /**
     * @var string The hashed password
     */
    #[ORM\Column]
	#[Groups("uzers")]
    private ?string $password = null;

    #[ORM\Column(length: 255)]
	#[Groups("uzers")]
	#[Assert\NotBlank(message : "Last name is required")]
	#[Assert\Length(min :3 ,minMessage :"Last Name needs to have at least 3 characters")]
    private ?string $nom = null;

    #[ORM\Column(length: 255)]
	#[Groups("uzers")]
	#[Assert\NotBlank(message : "First name is required")]
	#[Assert\Length(min :3 ,minMessage :"Name needs to have at least 3 characters")]
    private ?string $prenom = null;

    #[ORM\Column(nullable: true)]
	#[Groups("uzers")]
	#[Assert\Positive]
	#[Assert\Length(min :8,max : 11,minMessage :"Votre Numero doit être au moins {{ limit }} characters long",maxMessage : "Votre Numero ne peut pas dépasser {{ limit }} characters")]
    private ?int $phone = null;

    #[ORM\Column(length: 255, nullable: true)]
	#[Groups("uzers")]
    private ?string $adres = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $dateins = null;

    #[ORM\Column(type: Types::DATE_MUTABLE, nullable: true)]
    private ?\DateTimeInterface $dateD = null;

    #[ORM\Column(type: Types::DATE_MUTABLE, nullable: true)]
    private ?\DateTimeInterface $dateF = null;

    #[ORM\Column(length: 255, nullable: true)]
    private ?string $imgUsr = null;

    #[ORM\Column(type: 'boolean')]
    private $isVerified = false;

    #[ORM\OneToMany(mappedBy: 'userX', targetEntity: Reclamation::class)]
    private Collection $reclamationX;

    #[ORM\Column(type:"string", length:50, nullable:true)]
    private $reset_token;

    public function __construct()
    {
        $this->reclamationX = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
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

    /**
     * A visual identifier that represents this user.
     *
     * @see UserInterface
     */
    public function getUserIdentifier(): string
    {
        return (string) $this->email;
    }

    /**
     * @deprecated since Symfony 5.3, use getUserIdentifier instead
     */
    public function getUsername(): string
    {
        return (string) $this->email;
    }

    /**
     * @see UserInterface
     */
    public function getRoles(): array
    {
        $roles = $this->roles;
        // guarantee every user at least has ROLE_USER
        $roles[] = 'ROLE_USER';

        return array_unique($roles);
    }

    public function setRoles(array $roles): self
    {
        $this->roles = $roles;

        return $this;
    }

    /**
     * @see PasswordAuthenticatedUserInterface
     */
    public function getPassword(): string
    {
        return $this->password;
    }

    public function setPassword(string $password): self
    {
        $this->password = $password;

        return $this;
    }

    /**
     * Returning a salt is only needed, if you are not using a modern
     * hashing algorithm (e.g. bcrypt or sodium) in your security.yaml.
     *
     * @see UserInterface
     */
    public function getSalt(): ?string
    {
        return null;
    }

    /**
     * @see UserInterface
     */
    public function eraseCredentials()
    {
        // If you store any temporary, sensitive data on the user, clear it here
        // $this->plainPassword = null;
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

    public function getPhone(): ?int
    {
        return $this->phone;
    }

    public function setPhone(?int $phone): self
    {
        $this->phone = $phone;

        return $this;
    }

    public function getAdres(): ?string
    {
        return $this->adres;
    }

    public function setAdres(?string $adres): self
    {
        $this->adres = $adres;

        return $this;
    }

    public function getDateins(): ?\DateTimeInterface
    {
        return $this->dateins;
    }

    public function setDateins(\DateTimeInterface $dateins): self
    {
        $this->dateins = $dateins;

        return $this;
    }

    public function getDateD(): ?\DateTimeInterface
    {
        return $this->dateD;
    }

    public function setDateD(?\DateTimeInterface $dateD): self
    {
        $this->dateD = $dateD;

        return $this;
    }

    public function getDateF(): ?\DateTimeInterface
    {
        return $this->dateF;
    }

    public function setDateF(?\DateTimeInterface $dateF): self
    {
        $this->dateF = $dateF;

        return $this;
    }

    public function getImgUsr(): ?string
    {
        return $this->imgUsr;
    }

    public function setImgUsr(?string $imgUsr): self
    {
        $this->imgUsr = $imgUsr;

        return $this;
    }

    public function isVerified(): bool
    {
        return $this->isVerified;
    }

    public function setIsVerified(bool $isVerified): self
    {
        $this->isVerified = $isVerified;

        return $this;
    }

    /**
     * @return Collection<int, Reclamation>
     */
    public function getReclamationX(): Collection
    {
        return $this->reclamationX;
    }

    public function addReclamationX(Reclamation $reclamationX): self
    {
        if (!$this->reclamationX->contains($reclamationX)) {
            $this->reclamationX->add($reclamationX);
            $reclamationX->setUserX($this);
        }

        return $this;
    }

    public function removeReclamationX(Reclamation $reclamationX): self
    {
        if ($this->reclamationX->removeElement($reclamationX)) {
            // set the owning side to null (unless already changed)
            if ($reclamationX->getUserX() === $this) {
                $reclamationX->setUserX(null);
            }
        }

        return $this;
    }




        
    public function getResetToken(): ?string
    {
        return $this->reset_token;
    }

    public function setResetToken(?string $reset_token): self
    {
        $this->reset_token = $reset_token;

        return $this;
    }



    public function __toString() {
        return "[{id: " . $this->id . ", email: " . $this->email . ", password: " . $this->password . ", roles: " . implode(',', $this->roles) . ", nom: " . $this->nom . ", prenom: " . $this->prenom . ", phone: " . $this->phone . ", adres: " . $this->adres . ", dateins: " . "}]";
    }


    public function tojstring() {
        $json = array(
            'id' => $this->id,
            'email' => $this->email,
            'password' => $this->password,
            'roles' => $this->roles,
            'nom' => $this->nom,
            'prenom' => $this->prenom,
            'phone' => $this->phone,
            'adres' => $this->adres,
            'dateins' => $this->dateins
        );
        return json_encode($json);
    }




}
