package umc.CarrotMarket_Clone.src.board;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import umc.CarrotMarket_Clone.config.BaseException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

import static umc.CarrotMarket_Clone.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileService {
    private final BoardRepository boardRepository;
    @Value("${file.mainDir}")
    private String uploadDir; // application.yml에 설정한 default 저장장소


    public String fileUpload(MultipartFile multipartFile) throws BaseException{
        String originalName = multipartFile.getOriginalFilename(); // 기존 이름
        String uuid = UUID.randomUUID().toString(); // 랜덤 값
        String savefileName =  uuid + "_" + StringUtils.cleanPath(originalName); // 최종 저장할 때 이름
        String savefilePath = uploadDir + File.separator + savefileName;
        // File.separator : 프로그램이 실행중인 OS에 해당하는 구분자 리턴

        Path savePath = Paths.get(savefilePath);

        try {
            multipartFile.transferTo(savePath);

            // 아래의 방법을 사용해도 저장됨... 확인은 안해봄
            //Files.copy(Path source, Path target, StandardCopyOption.REPLACE_EXISTING);
            //Files.copy(multipartFile.getInputStream(), serverPath, StandardCopyOption.REPLACE_EXISTING); // StandardCopyOption.REPLACE_EXISTING : 파일 이미 존재할 경우 덮어쓰기

            return savefileName;
        } catch (IOException e) {
            throw new BaseException(FAIL_FILE_UPLOAD);
            //throw new FileStorageException("fail to store file");
        }
    }

    // 이미지 파일 자체를 줌, front에서는 <img src = "api경로">
    // 원래는 boardId를 받았지만, 그냥 fileName 받아서 처리하는 것으로
    public ResponseEntity<Resource> fileShow(String fileName) throws BaseException {
        //Optional<Board> findOne = boardRepository.findById(boardId);
        //Board board = findOne.orElseThrow(() -> new BaseException(FAIL_FIND_BOARD));

        //String fileName = board.getBoardImg();
        //if(fileName == null || fileName.equals("")){ // 게시물 작성할 때, 이미지 추가 하지 않은 경우
        //    return null;
        //}
        
        String fullPath = uploadDir + File.separator + fileName;
        System.out.println("full path = " + fullPath);

        try{
            Resource resource = new FileSystemResource(fullPath); // UrlResource("file:저장경로 + 파일명") 로컬에 저장된 파일 불러옴

            // 로컬 서버에 저장된 이미지 파일이 없을 경우
            /*if(!resource.exists()){
                return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND); // 리턴 결과 반환 404
            }*/

            HttpHeaders header = new HttpHeaders();
            Path filePath = null;
            filePath = Paths.get(fullPath);
            header.add("Content-Type", Files.probeContentType(filePath)); // 헐 이거 설정하니까 사진 나옴....

            return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);


            // 업로드 한 파일명이 한글인 경우 아래 작업을 안해주면 한글이 깨질 수 있음
            //String encodedUploadFileName = UriUtils.encode(uploadFilename, StandardCharsets.UTF_8);
            //String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        }catch (Exception e){
            throw new BaseException(CANNOT_FIND_FILE);
        }

    }
}
