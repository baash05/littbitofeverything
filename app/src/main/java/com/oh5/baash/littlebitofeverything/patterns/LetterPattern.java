package com.oh5.baash.littlebitofeverything.patterns;

import java.util.Random;

/**
 * Created by davidrawk on 3/01/15.
 */
public class LetterPattern {
        private int m_right_answer;
        private int m_size = 10;
        private char [] m_answers = null;
        private char [] m_pattern = null;

        static int A = 65;

        public LetterPattern(int size){
            m_right_answer = new Random().nextInt(26);
            m_size = size;
            A = (new Random().nextBoolean() ?  65 : 97);
        }
        public char get_right_answer(){
            return Character.toChars( A + m_right_answer )[0];
        }
        public char [] get_answers(){
            if( m_answers == null) {
                Random rand = new Random();
                m_answers = new char[3];
                int random_number;
                for(int x = 0; x < 3; x++){
                    random_number = rand.nextInt(7)+1;
                    if( rand.nextBoolean() ) {
                        random_number *= -1;
                    }
                    random_number = (m_right_answer + random_number + 26)%26;
                    m_answers[x] = Character.toChars( A + random_number )[0];
                }
                int right_index = rand.nextInt(3);
                m_answers[right_index] = get_right_answer();
            }
            return m_answers;
        }

        public char[] get_pattern(){
            if( m_pattern == null) {
                m_pattern = new char[m_size];
                int lowest = m_right_answer - (new Random().nextInt(m_size / 2));
                if(lowest < 0)              lowest = 0;
                if((lowest + m_size) > 26 ) lowest = 26-m_size;

                for (int x = 0; x < m_size; x++) {
                    m_pattern[x] =  Character.toChars( A + lowest + x )[0];
                }
                if(new Random().nextBoolean()) {
                    revers_pattern();
                }
            }
            return m_pattern;
        }

        private void revers_pattern(){
            for (int i = 0; i < m_size / 2; i++) {
                char temp = m_pattern[i];
                m_pattern[i] = m_pattern[m_size - 1 - i];
                m_pattern[m_size - 1 - i] = temp;
            }
        }
        public int size(){
            return m_size;
        }
}
