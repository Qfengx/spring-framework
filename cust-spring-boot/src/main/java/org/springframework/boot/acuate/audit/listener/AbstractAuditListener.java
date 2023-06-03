/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.acuate.audit.listener;

import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.context.ApplicationListener;

/**
 * Abstract {@link ApplicationListener} to handle {@link AuditApplicationEvent}s.
 *
 * @author Vedran Pavic
 * @since 1.4.0
 */
public abstract class AbstractAuditListener implements ApplicationListener<AuditApplicationEvent> {

	@Override
	public void onApplicationEvent(AuditApplicationEvent event) {
		onAuditEvent(event.getAuditEvent());
	}

	protected abstract void onAuditEvent(AuditEvent event);

}