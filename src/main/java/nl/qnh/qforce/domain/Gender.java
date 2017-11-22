/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.qnh.qforce.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The gender.
 *
 * @author QNH
 */
public enum Gender {
    MALE("male"), FEMALE("female"), UNKNOWN("unknown"), NOT_APPLICABLE("n/a");

    private String value;
    private Gender(String value) { this.value = value; }

    @JsonValue
    public String getValue() { return this.value; }

    /**
     *
     * @param val the json value that we receive
     * @return our internal enumeration value that is mapped to the json value
     */
    @JsonCreator
    public static Gender create(String val) {
        Gender[] genderValues = Gender.values();
        for (Gender gender : genderValues) {
            if (gender.getValue().equals(val)) {
                return gender;
            }
        }
        return null;
    }
}
