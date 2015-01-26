package com.oh5.baash.tinymonsters.patterns;

import java.util.Random;

/**
 * Created by davidrawk on 3/01/15.
 */
public class LetterPattern extends BasePattern {
        private int m_size = 10;

        static int A = 65;

        public LetterPattern(int size){
            description = "What letter is missing?";
            m_right_answer = m_randomiser.nextInt(26);
            m_size = size;
            A = (new Random().nextBoolean() ?  65 : 97);
        }
        @Override
        public String get_right_answer(){
            return "" + Character.toChars( A + m_right_answer )[0];
        }
        public String [] get_answers(){
            if( m_answers == null) {
                m_answers = new String[3];
                int [] answer_ints = get_answer_ints();
                for(int x = 0; x < 3; x++){
                    m_answers[x] = "" + Character.toChars( A + answer_ints[x])[0];
                }
            }
            return m_answers;
        }

        public String[] get_puzzle(){
            if( m_pattern == null) {
                m_pattern = new String[m_size];
                int lowest = m_right_answer - (m_randomiser.nextInt(m_size / 2) + 1);
                if(lowest < 0)              lowest = 0;
                if((lowest + m_size) > 26 ) lowest = 26-m_size;

                for (int x = 0; x < m_size; x++) {
                    m_pattern[x] = "" + Character.toChars( A + lowest + x )[0];
                }
            }
            return m_pattern;
        }

        public int get_size(){
            return m_size;
        }
}
