package au.gov.vic.ecodev.mrt.template.loader.fsm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.api.constants.Constants.Numeral;

public class ExtractTemplateState implements LoaderState {

	private static final String UNDER_LINE = "_";
	private static final String FILE_NAME_PATTERN = "^\\p{Alnum}+_\\p{Alnum}+\\.zip$";
	
	@Override
	public void on(TemplateLoaderStateMachineContext templateLoaderStateMachineContext) {
		List<String> fileNames = templateLoaderStateMachineContext.getMessage().getFileNames();
		if (CollectionUtils.isEmpty(fileNames)) {
			
		} else {
			List<String> zipFileNames = filterZipFile(fileNames);
			List<String> templateNames = extractTemplateName(zipFileNames);
			templateLoaderStateMachineContext.getMessage().setFileNames(templateNames);
		}
	}

	protected final  List<String> filterZipFile(List<String> fileNames) {
		return fileNames.stream()
			.filter(fileName -> fileName.matches(FILE_NAME_PATTERN))
			.collect(Collectors.toList());
	}
	
	protected final List<String> extractTemplateName(List<String> fileNames) {
		List<String> templateNames = new ArrayList<>();
		fileNames.stream()
			.forEach(fileName -> {
				String[] names = fileName.split(UNDER_LINE);
				templateNames.add(names[Numeral.ZERO]);
			});
		return templateNames;
	}
}
