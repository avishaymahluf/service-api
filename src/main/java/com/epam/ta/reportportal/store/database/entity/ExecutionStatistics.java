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

package com.epam.ta.reportportal.store.database.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Pavel Bortnik
 */
@Entity
@Table(name = "execution_statistics")
public class ExecutionStatistics implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "passed")
	private int passed;

	@Column(name = "failed")
	private int failed;

	@Column(name = "skipped")
	private int skipped;

	@Column(name = "total")
	private int total;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPassed() {
		return passed;
	}

	public void setPassed(int passed) {
		this.passed = passed;
	}

	public int getFailed() {
		return failed;
	}

	public void setFailed(int failed) {
		this.failed = failed;
	}

	public int getSkipped() {
		return skipped;
	}

	public void setSkipped(int skipped) {
		this.skipped = skipped;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
