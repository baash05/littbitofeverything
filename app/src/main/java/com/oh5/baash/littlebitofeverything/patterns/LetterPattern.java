package com.oh5.baash.littlebitofeverything.patterns;

import java.util.Random;

/**
 * Created by davidrawk on 3/01/15.
 */
public class LetterPattern extends PatternsBase{
        private int m_size = 10;

        static int A = 65;

        public LetterPattern(int size){
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
                int random_number;
                for(int x = 0; x < 3; x++){
                    random_number = m_randomiser.nextInt(7)+1;
                    if( m_randomiser.nextBoolean() ) {
                        random_number *= -1;
                    }
                    random_number = (m_right_answer + random_number + 26)%26;
                    m_answers[x] = "" + Character.toChars( A + random_number )[0];
                }
                int right_index = m_randomiser.nextInt(3);
                m_answers[right_index] = get_right_answer();
            }
            return m_answers;
        }

        public String[] get_puzzle(){
            if( m_pattern == null) {
                m_pattern = new String[m_size];
                int lowest = m_right_answer - (m_randomiser.nextInt(m_size / 2));
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
