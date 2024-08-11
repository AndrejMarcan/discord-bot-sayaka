/** By YamiY Yaten */
package mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.yatensoft.dcbot.core.dto.UrlArchiveDTO;
import com.yatensoft.dcbot.core.enumeration.ArchiveTypeEnum;
import com.yatensoft.dcbot.core.enumeration.TopicEnum;
import com.yatensoft.dcbot.core.mapper.BusinessObjectMapper;
import com.yatensoft.dcbot.core.persitence.model.UrlArchive;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BusinessObjectMapperTest {
    private static final String URL_ARCHIVE_URL = "http://testing-testing.com/123";
    private static final UUID URL_ARCHIVE_ID = UUID.randomUUID();

    private final BusinessObjectMapper mapper = new BusinessObjectMapper();

    @Test
    @DisplayName("Map UrlArchiveDTO to UrlArchive -> whole object")
    void shouldMapUrlArchiveDTOToUrlArchive() {
        final Date dateOfCreation = Date.from(Instant.now());
        final UrlArchiveDTO source = new UrlArchiveDTO.Builder()
                .id(URL_ARCHIVE_ID)
                .url(URL_ARCHIVE_URL)
                .type(ArchiveTypeEnum.ARTICLE)
                .topic(TopicEnum.COMMON)
                .dateOfCreation(dateOfCreation)
                .build();
        final UrlArchive result = mapper.mapUrlArchiveDtoToUrlArchive(source);

        assertEquals(URL_ARCHIVE_ID, result.getId());
        assertEquals(URL_ARCHIVE_URL, result.getUrl());
        assertEquals(TopicEnum.COMMON.getShortName(), result.getTopic());
        assertEquals(ArchiveTypeEnum.ARTICLE.getValue(), result.getType());
        assertEquals(dateOfCreation, result.getDateOfCreation());
    }

    @Test
    @DisplayName("Map UrlArchiveDTO to UrlArchive -> source object values are null")
    void shouldMapUrlArchiveDTOToUrlArchiveNulls() {
        final UrlArchiveDTO source = new UrlArchiveDTO();
        final UrlArchive result = mapper.mapUrlArchiveDtoToUrlArchive(source);

        assertNull(result.getId());
        assertNull(result.getUrl());
        assertNull(result.getTopic());
        assertNull(result.getType());
        assertNotNull(result.getDateOfCreation());
    }
}
