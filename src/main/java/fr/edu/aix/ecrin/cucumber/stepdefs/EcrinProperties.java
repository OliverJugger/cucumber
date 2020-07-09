package fr.edu.aix.ecrin.cucumber.stepdefs;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class EcrinProperties {

    public static final String URL_QUALIF = "url.qualif";
    public static final String URL_INT = "url.int";
    public static final String URL_DEV = "url.dev";

    public static final String PATH_COMPTE_RENDU = "path.compte_rendu";
    public static final String CAS_TESTS = "cas.tests";
    public static final String RES_CAS = "res.cas";

    // ONGLET DESCRIPTION
    public static final String BLOC_INFORMATIONS_GENELALES = "Informations Générales";
    public static final String BLOC_PROPRIETES_INDEMNITE = "Propriétés de l'indemnité";
    public static final String BLOC_TYPE_PERSONNEL = "Type de Personnel";
    public static final String BLOC_RETENUE_CONGES = "Retenue sur congés";
    public static final String BLOC_PAIEMENT = "Paiement";

    // ONGLET CONTROLES
    public static final String BLOC_CONTROLES_AFFECTATION = "Contrôles sur Affectation";
    public static final String BLOC_CONTROLES_GRADE_SANS_ORS = "Contrôles sur Grade sans ORS";
    public static final String BLOC_REGLES_EXCLUSION = "Règle exclusion";
    public static final String BLOC_IMPUTATION = "Imputation";

    // ONGLET COMPLEMENT ENSEIGNANTS
    public static final String BLOC_CONTROLES_GRADE_AVEC_ORS = "Contrôles sur Grade avec ORS";
    public static final String BLOC_MOTIF_INDEMNITE = "Motif Indemnité";

    // ONGLET PARAMETRAGE APPLICATIF
    public static final String BLOC_MODALITE_ZONES_SAISIE = "Modalité des Zones de Saisie";
    public static final String BLOC_CONTROLES_APPLICATIF = "Contrôles Applicatifs";
    public static final String BLOC_REGLE_FABRICATION_M22 = "Règle fabrication m22";
    public static final String BLOC_PLAFOND = "Plafond";
    public static final String BLOC_PRORATISATION = "Proratisation";

    // ONGLET DONNEES SPECIFIQUES
    public static final String BLOC_INTERVALLE = "Intervalle";
    public static final String BLOC_INDEMNITE_PROF_PRINCIPALE = "Indemnité Prof Principale";
    public static final String BLOC_HEURE_INTERROGATION = "Pour Heure d'Interrogation";

    // ONGLET INFOS PUBLICATIONS
    public static final String BLOC_HISTORIQUE_VERSIONS = "Historique des Versions";

    public static final int INDEMNITE_NUM_MAX = 9999;
    public static final int INDEMNITE_NUM_MIN = 1000;

    /**
     * Constructeur par défaut privatisé.
     */
    private EcrinProperties() {
    }

    /** Le bundle. */
    private static ResourceBundle bdleAsie;

    /**
     * @param strProperty
     *            Id de la propriété
     * @return La valeur de la propriété
     */
    public static String getString(String strProperty) {
        if (bdleAsie == null) {
            bdleAsie = ResourceBundle.getBundle("ecrin");
        }
        try {
            return bdleAsie.getString(strProperty);
        } catch (MissingResourceException e) {
            return null;
        }
    }

}
