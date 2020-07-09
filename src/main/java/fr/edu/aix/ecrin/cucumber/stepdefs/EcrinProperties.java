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
    public static final String BLOC_INFORMATIONS_GENELALES = "Informations G�n�rales";
    public static final String BLOC_PROPRIETES_INDEMNITE = "Propri�t�s de l'indemnit�";
    public static final String BLOC_TYPE_PERSONNEL = "Type de Personnel";
    public static final String BLOC_RETENUE_CONGES = "Retenue sur cong�s";
    public static final String BLOC_PAIEMENT = "Paiement";

    // ONGLET CONTROLES
    public static final String BLOC_CONTROLES_AFFECTATION = "Contr�les sur Affectation";
    public static final String BLOC_CONTROLES_GRADE_SANS_ORS = "Contr�les sur Grade sans ORS";
    public static final String BLOC_REGLES_EXCLUSION = "R�gle exclusion";
    public static final String BLOC_IMPUTATION = "Imputation";

    // ONGLET COMPLEMENT ENSEIGNANTS
    public static final String BLOC_CONTROLES_GRADE_AVEC_ORS = "Contr�les sur Grade avec ORS";
    public static final String BLOC_MOTIF_INDEMNITE = "Motif Indemnit�";

    // ONGLET PARAMETRAGE APPLICATIF
    public static final String BLOC_MODALITE_ZONES_SAISIE = "Modalit� des Zones de Saisie";
    public static final String BLOC_CONTROLES_APPLICATIF = "Contr�les Applicatifs";
    public static final String BLOC_REGLE_FABRICATION_M22 = "R�gle fabrication m22";
    public static final String BLOC_PLAFOND = "Plafond";
    public static final String BLOC_PRORATISATION = "Proratisation";

    // ONGLET DONNEES SPECIFIQUES
    public static final String BLOC_INTERVALLE = "Intervalle";
    public static final String BLOC_INDEMNITE_PROF_PRINCIPALE = "Indemnit� Prof Principale";
    public static final String BLOC_HEURE_INTERROGATION = "Pour Heure d'Interrogation";

    // ONGLET INFOS PUBLICATIONS
    public static final String BLOC_HISTORIQUE_VERSIONS = "Historique des Versions";

    public static final int INDEMNITE_NUM_MAX = 9999;
    public static final int INDEMNITE_NUM_MIN = 1000;

    /**
     * Constructeur par d�faut privatis�.
     */
    private EcrinProperties() {
    }

    /** Le bundle. */
    private static ResourceBundle bdleAsie;

    /**
     * @param strProperty
     *            Id de la propri�t�
     * @return La valeur de la propri�t�
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
