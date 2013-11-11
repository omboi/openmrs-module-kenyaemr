/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.kenyaemr.reporting.library.shared.mchcs;



import org.openmrs.Concept;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.reporting.library.shared.common.CommonCohortLibrary;
import org.openmrs.module.reporting.cohort.definition.AgeCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.common.DurationUnit;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Library of MCH-MS related cohort definitions
*/
@Component
public class MchcsCohortLibrary {

	@Autowired
	private CommonCohortLibrary commonCohorts;

	public CohortDefinition pcrWithinMonths() {

		Concept pcrTest = Dictionary.getConcept(Dictionary.HIV_DNA_POLYMERASE_CHAIN_REACTION);
		Concept detected = Dictionary.getConcept(Dictionary.DETECTED);
		Concept equivocal = Dictionary.getConcept(Dictionary.EQUIVOCAL);
		Concept inhibitory = Dictionary.getConcept(Dictionary.INHIBITORY);
		Concept poorSampleQuality = Dictionary.getConcept(Dictionary.POOR_SAMPLE_QUALITY);
		return commonCohorts.hasObs(pcrTest,detected,equivocal,inhibitory,poorSampleQuality);
	}

	public CohortDefinition pcrInitialTest() {
		Concept contexualStatus = Dictionary.getConcept(Dictionary.TEXT_CONTEXT_STATUS);
		Concept initial = Dictionary.getConcept(Dictionary.TEST_STATUS_INITIAL);
		return commonCohorts.hasObs(contexualStatus,initial);
	}

	public CohortDefinition age2Months() {
		AgeCohortDefinition age = new AgeCohortDefinition();
		age.setName("Children with 2 months of age");
		age.setMaxAge(2);
		age.setMaxAgeUnit(DurationUnit.MONTHS);
		age.addParameter(new Parameter("effectiveDate", "effective date", Date.class));
		return age;
	}
	//HV02-24
	public CohortDefinition  pcrInitialWithin2Months() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("pcrWithinMonths", ReportUtils.map(pcrWithinMonths(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("pcrInitialTest", ReportUtils.map(pcrInitialTest(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("paeds2Months", ReportUtils.map(age2Months(), "effectiveDate=${onOrBefore}"));
		cd.setCompositionString("pcrWithinMonths AND pcrInitialTest AND paeds2Months");
		return cd;
	}

	public CohortDefinition ageBetween3And8Months() {
		AgeCohortDefinition age = new AgeCohortDefinition();
		age.setName("Children Between 2 and 8 Months");
		age.setMinAge(3);
		age.setMaxAge(8);
		age.setMinAgeUnit(DurationUnit.MONTHS);
		age.setMaxAgeUnit(DurationUnit.MONTHS);
		age.addParameter(new Parameter("effectiveDate", "effective date", Date.class));
		return age;
	}
	//HV02-25
	public CohortDefinition pcrInitialBetween3To8Months() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("pcrWithinMonths", ReportUtils.map(pcrWithinMonths(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("pcrInitialTest", ReportUtils.map(pcrInitialTest(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("ageBetween3And8Months", ReportUtils.map(ageBetween3And8Months(), "effectiveDate=${onOrBefore}"));
		cd.setCompositionString("pcrWithinMonths AND pcrInitialTest AND ageBetween3And8Months");
		return cd;
	}

	public CohortDefinition serologyAntBodyTest() {
		Concept hivRapidTest = Dictionary.getConcept(Dictionary.HIV_RAPID_TEST_1_QUALITATIVE);
		Concept negative = Dictionary.getConcept(Dictionary.NEGATIVE);
		Concept poorSampleQuality = Dictionary.getConcept(Dictionary.POOR_SAMPLE_QUALITY);
		Concept positive = Dictionary.getConcept(Dictionary.POSITIVE);
		return commonCohorts.hasObs(hivRapidTest,negative,poorSampleQuality,positive);
	}

	public CohortDefinition ageBetween9And12Months() {
		AgeCohortDefinition age = new AgeCohortDefinition();
		age.setName("Children Between 9 and 12 Months");
		age.setMinAge(9);
		age.setMaxAge(12);
		age.setMinAgeUnit(DurationUnit.MONTHS);
		age.setMaxAgeUnit(DurationUnit.MONTHS);
		age.addParameter(new Parameter("effectiveDate", "effective date", Date.class));
		return age;
	}
	//HV02-26
	public CohortDefinition serologyAntBodyTestBetween9And12Months() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("serologyAntBodyTest", ReportUtils.map(serologyAntBodyTest(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("ageBetween9And12Months", ReportUtils.map(ageBetween9And12Months(), "effectiveDate=${onOrBefore}"));
		cd.setCompositionString("serologyAntBodyTest AND ageBetween9And12Months");
		return cd;
	}

	public CohortDefinition pcrBetween9And12MonthsAge() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("pcrWithinMonths", ReportUtils.map(pcrWithinMonths(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("pcrInitialTest", ReportUtils.map(pcrInitialTest(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("ageBetween9And12Months", ReportUtils.map(ageBetween9And12Months(), "effectiveDate=${onOrBefore}"));
		cd.setCompositionString("pcrWithinMonths AND pcrInitialTest  AND ageBetween9And12Months");
		return cd;
	}

	public CohortDefinition totalHeitestedBy12Months() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("pcrInitialWithin2Months", ReportUtils.map(pcrInitialWithin2Months(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("pcrInitialBetween3To8Months", ReportUtils.map(pcrInitialBetween3To8Months(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("serologyAntBodyTestBetween9And12Months", ReportUtils.map(serologyAntBodyTestBetween9And12Months(), "effectiveDate=${onOrBefore}"));
		cd.setCompositionString("pcrInitialWithin2Months OR pcrInitialBetween3To8Months OR serologyAntBodyTestBetween9And12Months");
		return cd;
	}
}

