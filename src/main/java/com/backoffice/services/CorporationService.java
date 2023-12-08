package com.backoffice.services;

//@Service
public class CorporationService {

   /* @Autowired
    private CorporationRepository corporationRepository;
    @Autowired
    private TelephoneRepository telephoneRepository;
    @Autowired
    private AdresseRepository adresseRepository;

    Logger logger = LoggerFactory.getLogger(Corporation.class);

    public Corporation createCorporation(Corporation corporation){
        Adresse adresse = corporation.getAdresse();
        if(corporationRepository.findByEmail(corporation.getEmail()).isEmpty()){
            /** on commence tout d'abord par enregistrer l'adresse dans la base des données */
         /*   Adresse newAdr = adresseRepository.save(adresse);
            logger.info("l'adresse : "+adresse.getAdresse());
            logger.info("Code postal : "+adresse.getCodePostal());
            logger.info("Ville : "+adresse.getVille());
            logger.info("Vient d'être Enregistré dans la base des données");
            corporation.setAdresse(newAdr);
            logger.warn("************************************************************************");
            /** on commence par enregistrer le numéro de telephone */
        /*    Telephone telephone = corporation.getTelephone();
            logger.info("On verifie si le numero "+telephone.getNumero()+ " existe dans la base des données ");
            if(telephoneRepository.findByNumero(telephone.getNumero()).isEmpty()){
                Telephone newTel =telephoneRepository.save(telephone);
                logger.info("Numéro de téléphone de la société enregisté avec succès ");
                corporation.setTelephone(newTel);
            }else{
                throw new IllegalArgumentException("Le numéro de téléphone saisi existe déjà dans la base des données");
            }
            corporation.setTelephone(telephone);
            Corporation newCorp = corporationRepository.save(corporation);
            logger.info("La société "+newCorp.getLibelle()+" a été enregistrée avec succès ");
            return newCorp;

        }else{
            logger.error("Impossible d'enregistrer la société car l'adresse Email est déjà attribué a une autre société ");
            throw new IllegalArgumentException("L'adresse Email "+corporation.getEmail()+ " existe déjà dans la base des données ");
        }
    }

    public Corporation updateCorporation(Corporation update, long id ){
        Corporation corp = corporationRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid ID not found "));
        logger.info("Début de la mise à jour des informations de la société ");
        corp.setEmail(update.getEmail());
        corp.setCa(update.getCa());
        corp.setLibelle(update.getLibelle());
        corp.setStatus(update.getStatus());
        corp.setSiret(update.getSiret());
        corp.setTelephone(update.getTelephone());
        corp.setAccounts(update.getAccounts());
        corporationRepository.save(corp);
        logger.info("Fin de la mise à jour des information de la société "+corp.getLibelle());
        return corp;
    }

    /**
     * @param corporation : recevra les information de la société
     * @param account : a son recevra les information néecessaire de l'utilsateur
     * */
   /* public Corporation associateCorporationToAccount(Corporation corporation, Account account){
        if(corporationRepository.existsById(corporation.getId())){
            corporation.getAccounts().add(account);
            corporationRepository.save(corporation);
            return corporation;
        }else {
            throw new IllegalArgumentException("Une erreur s'est produite lors de l'ajout de l'utilisateur à la sociéte");
        }
    }*/
}
