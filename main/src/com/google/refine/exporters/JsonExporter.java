package com.google.refine.exporters;

import com.google.refine.browsing.Engine;
import com.google.refine.browsing.RowVisitor;
import com.google.refine.model.Project;
import com.google.refine.model.Row;
import com.google.refine.model.Cell;

import java.io.IOException;
import java.io.Writer;
import java.util.Properties;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;

/**
 * Created by jona on 23/02/15.
 */
public class JsonExporter implements WriterExporter {
	@Override
	public String getContentType() { return "application/json"; }

	@Override
	public void export(final Project project, Properties params, Engine engine, final Writer writer)
					throws IOException {
		JsonFactory factory = new JsonFactory();
		final JsonGenerator jsonWriter = factory.createJsonGenerator(writer);

		// do something, here.
		RowVisitor visitor = new RowVisitor() {
			int rowCount = 0;

			@Override
			public void start(Project project) {
				jsonWriter.writeStartArray();
			}

			@Override
			public boolean visit(Project project, int rowIndex, Row row) {
				jsonWriter.writeStartArray();

				for (Cell cell : row.cells) {
					jsonWriter.writeStartObject();

					jsonWriter.writeNumberField("row", rowIndex);
					jsonWriter.writeNumberField("col", 1);

					jsonWriter.writeStringField("value", cell.value);

					jsonWriter.writeEndObject();
				}

				jsonWriter.writeEndArray();
			}

			@Override
			public void end(Project project) {
				jsonWriter.writeEndArray();
			}
		}
	}
}
