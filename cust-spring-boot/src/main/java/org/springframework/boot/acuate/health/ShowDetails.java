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

package org.springframework.boot.acuate.health;

/**
 * Options for showing details in responses from the {@link HealthEndpoint} web
 * extensions.
 *
 * @author Andy Wilkinson
 * @since 2.0.0
 */
public enum ShowDetails {

	/**
	 * Never show details in the response.
	 */
	NEVER,

	/**
	 * Show details in the response when accessed by an authorized user.
	 */
	WHEN_AUTHORIZED,

	/**
	 * Always show details in the response.
	 */
	ALWAYS

}
