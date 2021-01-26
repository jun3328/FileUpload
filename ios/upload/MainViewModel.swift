//
//  MainViewModel.swift
//  upload
//
//  Created by lee on 2021/01/26.
//

import Alamofire
import Moya
import RxSwift

class MainViewModel {
    
    let provider = MoyaProvider<MyAPI>()
    
    var disposeBag = DisposeBag()
    
    func uploadImage(imageData: Data) {
        let alamoFire = AF.upload(
            multipartFormData: { formData in
                formData.append("ios".data(using: .utf8)!, withName: "folder", mimeType: "text/plain")
                formData.append(imageData, withName: "photos", fileName: "test.png", mimeType: "image/*")
                formData.append(imageData, withName: "photos", fileName: "test2.png", mimeType: "image/*")
            },
            to: "http://192.168.1.42:3000/upload", method: .post) // { print("리절또 URL \($0)") }
        
        alamoFire.responseString { (response) in
            switch response.result {
            case .success(let data):
                print("성공 \(data)")
            case .failure(let error):
                print("에러 \(error.localizedDescription)")
            }
        }
    }
    
    func uploadImageMoya(imageData: Data) {
        provider.rx.request(.upload(image: imageData))
            .filterSuccessfulStatusCodes()
            .map(Result.self)
            .subscribe { (response) in
                print("(업로드) : \(response.result)")
            } onError: { (error) in
                print(error.localizedDescription)
            }.disposed(by: disposeBag)
    }
    
    deinit {
        disposeBag = DisposeBag()
    }
}
