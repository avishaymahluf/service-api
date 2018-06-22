/*
 * Copyright 2017 EPAM Systems
 *
 *
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/service-api
 *
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.epam.ta.reportportal.store.database.dao;

import com.epam.ta.reportportal.store.database.entity.enums.StatusEnum;
import com.epam.ta.reportportal.store.jooq.tables.JTestItem;
import com.epam.ta.reportportal.store.jooq.tables.JTestItemResults;
import com.epam.ta.reportportal.store.jooq.tables.JTestItemStructure;
import com.google.common.collect.ImmutableMap;
import org.jooq.DSLContext;
import org.jooq.Field;

import java.util.Map;

import static com.epam.ta.reportportal.store.jooq.Tables.*;
import static org.jooq.impl.DSL.*;

/**
 * @author Pavel Bortnik
 */
public class ExecutionStatisticsRepositoryCustomImpl implements ExecutionStatisticsRepositoryCustom {

	private static final Integer COUNTER_STEP = 1;

	private static final Map<StatusEnum, Field<Integer>> STATUS_FIELD_MAPPING = ImmutableMap.<StatusEnum, Field<Integer>>builder().put(
			StatusEnum.PASSED, EXECUTION_STATISTICS.PASSED).put(StatusEnum.SKIPPED, EXECUTION_STATISTICS.SKIPPED).build();

	private DSLContext dsl;

	public void setDsl(DSLContext dsl) {
		this.dsl = dsl;
	}

	@Override
	public void updateExecutionStatistics(StatusEnum status, Long rootItemId, boolean increment) {
		Field<Integer> updateField = STATUS_FIELD_MAPPING.getOrDefault(status, EXECUTION_STATISTICS.FAILED);
		JTestItemStructure tis = TEST_ITEM_STRUCTURE.as("tis");
		JTestItem ti = TEST_ITEM.as("ti");
		JTestItemResults tir = TEST_ITEM_RESULTS.as("tir");
		String structure = "item_structure";

		dsl.update(EXECUTION_STATISTICS)
				.set(updateField, increment ? updateField.add(COUNTER_STEP) : updateField.minus(COUNTER_STEP))
				.set(EXECUTION_STATISTICS.TOTAL,
						increment ? EXECUTION_STATISTICS.TOTAL.add(COUNTER_STEP) : EXECUTION_STATISTICS.TOTAL.minus(COUNTER_STEP)
				)
				.where(EXECUTION_STATISTICS.ID.in(dsl.withRecursive(structure, "parent_id", "item_id", "statistics_id")
						.as(dsl.select(tis.PARENT_ID, tir.ITEM_ID, tir.EXECUTION_STATISTICS)
								.from(tis)
								.join(tir)
								.on(tis.ITEM_ID.eq(tir.ITEM_ID))
								.where(tir.ITEM_ID.eq(rootItemId))
								.unionAll(dsl.select(tis.PARENT_ID, tis.ITEM_ID, tir.EXECUTION_STATISTICS)
										.from(table(structure), tis)
										.join(tir)
										.on(tis.ITEM_ID.eq(tir.ITEM_ID))
										.where(tis.ITEM_ID.eq(field(name(structure, "parent_id"), Long.class)))))
						.select(field(name(structure, "statistics_id"), Long.class))
						.from(name(structure))
						.unionAll(dsl.select(LAUNCH.EXECUTION_STATISTICS)
								.from(LAUNCH)
								.join(ti)
								.on(LAUNCH.ID.eq(ti.LAUNCH_ID))
								.where(ti.ITEM_ID.eq(rootItemId)))))
				.execute();
	}
}
