package net.vishalsrivastava.space.efficient.probabilistic;

import net.vishalsrivastava.space.efficient.common.ShuffleArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class BloomFilterTest {

    @Test
    void testWithInput() {
        BloomFilter<String> stringBloomFilter = new BloomFilter<>(20, 0.05);

        String[] wordPresent = {"abound", "abounds", "abundance", "abundant", "accessable",
                "bloom", "blossom", "bolster", "bonny", "bonus", "bonuses",
                "coherent", "cohesive", "colorful", "comely", "comfort",
                "gems", "generosity", "generous", "generously", "genial"};


        String[] wordAbsent = {"bluff", "cheater", "hate", "war", "humanity",
                "racism", "hurt", "nuke", "gloomy", "facebook",
                "geeksforgeeks", "twitter"};


        for (String item : wordPresent) {
            stringBloomFilter.add(item);
        }

        ShuffleArray.shuffle(wordPresent);
        ShuffleArray.shuffle(wordAbsent);

        String[] testData = new String[wordAbsent.length + 10];
        for (int i = 0; i < testData.length; i++) {
            if (i < 10) {
                testData[i] = wordPresent[i];
            } else {
                testData[i] = wordAbsent[i - 10];
            }
        }

        ShuffleArray.shuffle(testData);

        for (String word : testData) {
            if (stringBloomFilter.exists(word)) {
                if (Arrays.asList(wordAbsent).contains(word)) {
                    System.out.printf("%s is a false positive%n", word);
                } else {
                    System.out.printf("%s is probably present%n", word);
                }
            } else {
                System.out.printf("%s is definitely not present%n", word);
            }
        }

        Assertions.assertNotNull(stringBloomFilter);
    }

}