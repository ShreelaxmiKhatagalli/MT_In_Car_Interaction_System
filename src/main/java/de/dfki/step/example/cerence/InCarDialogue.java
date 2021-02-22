package de.dfki.step.example.cerence;

import de.dfki.step.blackboard.IToken;
import de.dfki.step.blackboard.Rule;
import de.dfki.step.blackboard.Token;
import de.dfki.step.blackboard.conditions.PatternCondition;
import de.dfki.step.blackboard.patterns.Pattern;
import de.dfki.step.blackboard.patterns.PatternBuilder;
import de.dfki.step.blackboard.rules.SimpleRule;
import de.dfki.step.cerence.NLUController;
import de.dfki.step.dialog.Dialog;
import de.dfki.step.kb.semantic.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InCarDialogue extends Dialog {
    private static final Logger log = LoggerFactory.getLogger(InCarDialogue.class);
    public static de.dfki.step.cerence.NLUController cerence;

    public InCarDialogue() {
        // Adding types to KB
        try {
            Type type_WakeUpIntent = new Type("WakeUpIntent", this.getKB());
            Type type_gIEInfoObject = new Type("gIEInfoObject", this.getKB());
            Type type_gIEInfoUsesOfObject = new Type("gIEInfoUsesOfObject", this.getKB());
            Type type_gIEInfoHowToUse = new Type("gIEInfoHowToUse", this.getKB());

            this.getKB().addType(type_WakeUpIntent);
            this.getKB().addType(type_gIEInfoObject);
            this.getKB().addType(type_gIEInfoUsesOfObject);
            this.getKB().addType(type_gIEInfoHowToUse);


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong with adding types to KB");
        }

        // Starting Mix Service
        try {
            cerence = new NLUController(this);
            cerence.InitCerenceMix("wss://autotesting-azure-use2-cvt1-voice-ws.nuancemobility.net/v2",
                    "NMDPTRIAL_shreelaxmi_khatagalli_dfki_de_20201204T111343",
                    "E3A71FDD404FC633B5D7736BBF770A9F3B7EB6ADCE0A0D77E680529D6B0CA810645B72D0BA4E0EEB00F13532E855FAA2650AFCE77447BEB702780C7EEA157A0E",
                    "A1313_C7500",
                    "eng-USA");
            cerence.StartAudioServer("0.0.0.0", 50002);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong with cerence mix");
        }

        // Adding Rules
        try {
            Rule WakeUpIntent_Rule = new SimpleRule(tokens -> {
                IToken t = tokens[0];
                System.out.println("Hello! What can I do for you today?");
            }, "WakeUpIntent_Rule");
            Pattern p = new PatternBuilder("WakeUpIntent", this.getKB()).build();
            WakeUpIntent_Rule.setCondition(new PatternCondition(p));
            this.getBlackboard().addRule(WakeUpIntent_Rule);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong with adding rules");
        }

        try {
            Rule gIEInfoObject_Rule = new SimpleRule(tokens -> {
                IToken t = tokens[0];
                System.out.println("I am not yet trained to provide you the information about the objects");
            }, "gIEInfoObject_Rule");
            Pattern p = new PatternBuilder("gIEInfoObject", this.getKB()).build();
            gIEInfoObject_Rule.setCondition(new PatternCondition(p));
            this.getBlackboard().addRule(gIEInfoObject_Rule);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong with adding rules");
        }

        try {
            Rule gIEInfoUsesOfObject_Rule = new SimpleRule(tokens -> {
                IToken t = tokens[0];
                System.out.println("I am not yet trained to provide you the information on the uses of objects visible in cockpit");
            }, "gIEInfoUsesOfObject_Rule");
            Pattern p = new PatternBuilder("gIEInfoUsesOfObject", this.getKB()).build();
            gIEInfoUsesOfObject_Rule.setCondition(new PatternCondition(p));
            this.getBlackboard().addRule(gIEInfoUsesOfObject_Rule);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong with adding rules");
        }

        try {
            Rule gIEInfoHowToUse_Rule = new SimpleRule(tokens -> {
                IToken t = tokens[0];
                System.out.println("I am not yet trained to provide you the information on how to use or operate the objects visible in cockpit");
            }, "gIEInfoHowToUse_Rule");
            Pattern p = new PatternBuilder("gIEInfoHowToUse", this.getKB()).build();
            gIEInfoHowToUse_Rule.setCondition(new PatternCondition(p));
            this.getBlackboard().addRule(gIEInfoHowToUse_Rule);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong with adding rules");
        }

    }
}
