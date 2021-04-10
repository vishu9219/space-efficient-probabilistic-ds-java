package net.vishalsrivastava.space.efficient.probabilistic;

import net.vishalsrivastava.space.efficient.common.ShuffleArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class BloomFilterTest {

    @Test
    void testWithInput() {
        BloomFilter<String> stringBloomFilter = new BloomFilter<>(20, 0.05);

        String[] wordPresent = {"abound", "abounds", "abundance", "abundant", "accessible",
                "bloom", "blossom", "bolster", "bonny", "bonus", "bonuses",
                "coherent", "cohesive", "colorful", "comely", "comfort",
                "gems", "generosity", "generous", "generously", "genial"};


        String[] wordAbsent = {"bluff", "cheater", "hate", "war", "humanity",
                "racism", "hurt", "nuke", "gloomy", "facebook",
                "geeksforgeeks", "twitter"};

        Set<String> falsePositive = new HashSet<>();
        falsePositive.add("twitter");
        falsePositive.add("bluff");
        falsePositive.add("hate");

        Set<String> probablyPresent = new HashSet<>();
        probablyPresent.add("abound");
        probablyPresent.add("abounds");
        probablyPresent.add("abundance");
        probablyPresent.add("abundant");
        probablyPresent.add("accessible");
        probablyPresent.add("bloom");
        probablyPresent.add("blossom");
        probablyPresent.add("bolster");
        probablyPresent.add("bonny");
        probablyPresent.add("bonus");
        probablyPresent.add("bonuses");
        probablyPresent.add("coherent");
        probablyPresent.add("cohesive");
        probablyPresent.add("colorful");
        probablyPresent.add("comely");
        probablyPresent.add("comfort");
        probablyPresent.add("gems");
        probablyPresent.add("generosity");
        probablyPresent.add("generous");
        probablyPresent.add("generously");
        probablyPresent.add("genial");

        Set<String> notPresent = new HashSet<>();
        notPresent.add("nuke");
        notPresent.add("racism");
        notPresent.add("humanity");
        notPresent.add("cheater");
        notPresent.add("war");
        notPresent.add("facebook");
        notPresent.add("gloomy");
        notPresent.add("hurt");
        notPresent.add("geeksforgeeks");


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
                    if (!falsePositive.contains(word)) {
                        Assertions.fail(String.format("%s should be false positive", word));
                    }
                } else {
                    if (!probablyPresent.contains(word))
                        Assertions.fail(String.format("%s should be probably present", word));
                }
            } else {
                if (!notPresent.contains(word)) {
                    Assertions.fail(String.format("%s should be definitely not present", word));
                }
            }
        }

        Assertions.assertNotNull(stringBloomFilter);
    }

}