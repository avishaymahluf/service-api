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

/**
 * @author Pavel Bortnik
 */
public interface ExecutionStatisticsRepositoryCustom {

	/**
	 * Recursive execution statistics update of parents for provided root item by status.
	 *
	 * @param status     Status of execution that's counter should be updated
	 * @param rootItemId Root item id
	 * @param increment  Flag that shows if statistics should be incremented or decreased
	 */
	void updateExecutionStatistics(StatusEnum status, Long rootItemId, boolean increment);
}
