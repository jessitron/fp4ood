package com.jessitron.functionalprinciples;

import org.biojava3.core.sequence.*;

public class PainOfMutability {

    public static void main(String[] args) throws Exception {
        ChromosomeSequence ctgctgaacgtatcgat = new ChromosomeSequence("CTGCTGAACGTATCGAT");

        GeneSequence geneSequence = new GeneSequence(ctgctgaacgtatcgat, 1, 10, Strand.POSITIVE);

        TranscriptSequence transcriptSequence = new TranscriptSequence(geneSequence, 1, 10);

        transcriptSequence.addCDS(new AccessionID("blah"), 1, 5, 0);

       // transcriptSequence.getProteinSequence();


        gratuitousMethodToIncreaseTheLengthOfTheStackTrace();
    }

    private static void gratuitousMethodToIncreaseTheLengthOfTheStackTrace() {
        gratuitousMethod2();
    }

    private static void gratuitousMethod2() {
        throw new IllegalArgumentException("Invalid strand: +");
    }
}
