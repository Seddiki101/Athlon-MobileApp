<?php

namespace App\Controller;

use App\Entity\User;
use App\Repository\UserRepository;

use App\Security\EmailVerifier;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Mailer\Bridge\Google\Transport\GmailSmtpTransport;
use Symfony\Component\Mailer\Mailer;
use Symfony\Component\Mime\Email;
use Symfony\Component\Routing\Annotation\Route;

use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\HttpFoundation\JsonResponse;
use Twig\Environment;
use Twig\Loader\FilesystemLoader;
use Symfony\Component\Mailer\MailerInterface;
use SymfonyCasts\Bundle\VerifyEmail\VerifyEmailHelperInterface;
use SymfonyCasts\Bundle\VerifyEmail\Controller\VerifyEmailControllerTrait;
use SymfonyCasts\Bundle\VerifyEmail\Exception\VerifyEmailExceptionInterface;
use App\Form\RegistrationFormType;
use App\Security\LoginFormAuthenticator;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bridge\Twig\Mime\TemplatedEmail;

use Symfony\Component\Mime\Address;
use Symfony\Component\PasswordHasher\Hasher\UserPasswordHasherInterface;

use Symfony\Component\Security\Http\Authentication\UserAuthenticatorInterface;
use Symfony\Contracts\Translation\TranslatorInterface;
use Symfony\Component\Security\Core\Encoder\PlaintextPasswordEncoder;



class UserServiceController extends AbstractController
{
    //attributes
    private EmailVerifier $emailVerifier;
    private $verifyEmailHelper;
    private $mailer;



    #[Route('/userService', name: 'app_user_service')]
    public function index(): Response
    {
        return $this->render('user_service/index.html.twig', [
            'controller_name' => 'UserServiceController',
        ]);
    }
	
	
	
	
		///////////////////////////////////////////////////LOGIN////////////////////////////////////////////////////////////

    #[Route("/signin", name: "app_service_login")]
    public function siginAction(Request $request, NormalizerInterface $normalizer)
    {
        $Email = $request->query->get("email");
        $Password = $request->query->get("password");

        $em = $this->getDoctrine()->getManager();
        $user = $em->getRepository(User::class)->findOneBy(['email' => $Email]);

        if ($user) {
            if (  password_verify( $Password , $user->getPassword() )   )  {
                // $userlogin = $normalizer->normalize($user, 'json', ['groups' => "uzers"]);
                // $json = json_encode($userlogin);
               // echo("just for testing ");


                $json = array(
                    'id' => $user->getId(),
                    'email' => $user->getEmail(),
                    'password' => $user->getPassword(),
                    'roles' => $user->getRoles(),
                    'nom' => $user->getNom(),
                    'prenom' => $user->getPrenom(),
                    'phone' => $user->getPhone(),
                    'adres' => $user->getAdres()
                );

                $json2 = json_encode($json);

                return new Response( $json2 );

               // return new JsonResponse($json);
               // return new JsonResponse( $user );


            }
            else {
                return new Response("failed"); //password wrong
            }
        } 	else {
            return new Response("failed"); //no identical email
        }
    }



	
	
	
	
	///////////////////////////////////////////////////REGISTER////////////////////////////////////////////////////////////

	
	
	
	
	
	
	
	
	
	
	

	
	
	///////////////////////////////////////////////////Affichage liste////////////////////////////////////////////////////////////
	#[Route("/AllUserService", name: "listUserS")]
	public function getUsers(UserRepository $repo, SerializerInterface $serializer)
    {
		$lista = $repo->findAll();
		$json = $serializer->serialize($lista, 'json', ['groups' => "uzers"]);
		    return new Response($json);
    }
	
	///////////////////////////////////////////////////find one////////////////////////////////////////////////////////////
	
	    #[Route("/userService/{id}", name: "userServiceID")]
    public function UserId($id, NormalizerInterface $normalizer, UserRepository $repo)
    {
        $usr = $repo->find($id);
        $usrNormalises = $normalizer->normalize($usr, 'json', ['groups' => "uzers"]);
        return new Response(json_encode($usrNormalises));
    }

	///////////////////////////////////////////////////Ajout////////////////////////////////////////////////////////////

    #[Route("addUserJSON/new", name: "addUserJSON")]
    public function addUserJSON(Request $req,   NormalizerInterface $Normalizer)
    {

        $em = $this->getDoctrine()->getManager();
        $usr = new User();
		
		
        $usr->setNom($req->get('nom'));
		$usr->setPrenom($req->get('prenom'));
        $usr->setEmail($req->get('email'));
		//might need to encrypt it
		$usr->setPassword( $req->get('password') );
		 
		 $usr->setDateins(new \DateTime('now')) ;
         $usr->setRoles((["ROLE_USER"])) ;
		
        $em->persist($usr);
        $em->flush();

        //send mail
        /*
        $signatureComponents = $this->verifyEmailHelper->generateSignature(
            'registration_confirmation_route',
            23,
            $req->get('email')
        );

        $loader = new FilesystemLoader('../templates');
        $twig = new Environment($loader);
        $html = $twig->render('registration/confirmation_email.html.twig', ['signedUrl' => $signatureComponents->getSignedUrl()]);
         */
        $email = (new Email())
            ->from('athlontn@gmail.com')
            ->to($usr->getEmail())
            ->subject('Verfification mail')
            ->text("welcome to Athlon");
            //->html($html);



        $transport = new GmailSmtpTransport('athlontn@gmail.com','vbmcqlujlyxnftax');
        $mailer = new Mailer($transport);
        $mailer->send($email);

        //




        $jsonContent = $Normalizer->normalize($usr, 'json', ['groups' => 'uzers']);
        return new Response(json_encode($jsonContent));
    }
	
	///////////////////////////////////////////////////Edition////////////////////////////////////////////////////////////

    #[Route("updateUsrJSON/mod", name: "updateUserJSON")]
    public function updateUserJSON(Request $req, NormalizerInterface $Normalizer)
    {
        $id=$req->get('id');


        $em = $this->getDoctrine()->getManager();
        $usr = $em->getRepository(User::class)->find($id);

        $usr->setNom($req->get('nom'));
        $usr->setPrenom($req->get('prenom'));
        $usr->setPassword($req->get('password'));
        $usr->setEmail($req->get('email'));


        $em->flush();

        $jsonContent = $Normalizer->normalize($usr, 'json', ['groups' => 'uzers']);
        return new Response("User updated successfully " . json_encode($jsonContent));
    }

		
		///////////////////////////////////////////////////Suppression////////////////////////////////////////////////////////////
		

    #[Route("deleteUserJSON/{id}", name: "deleteUserJSON")]
    public function deleteUserJSON(Request $req, $id, NormalizerInterface $Normalizer)
    {

        $em = $this->getDoctrine()->getManager();
        $usr = $em->getRepository(User::class)->find($id);
        $em->remove($usr);
        $em->flush();
        $jsonContent = $Normalizer->normalize($usr, 'json', ['groups' => 'uzers']);
        return new Response("User deleted successfully " . json_encode($jsonContent));
    }
	
	
	
	
	
	
	
	
	
}
