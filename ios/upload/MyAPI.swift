//
//  MyAPI.swift
//  upload
//
//  Created by lee on 2021/01/26.
//

import Moya

enum MyAPI {
    case upload(image: Data)
}

extension MyAPI : TargetType {
    var baseURL: URL {
        URL(string: "http://192.168.1.42:3000")!
    }
    
    var path: String {
        switch self {
        case .upload:
            return "/upload"
        }
    }
    
    var method: Moya.Method {
        switch self {
        case .upload:
            return .post
        }
    }
    
    var sampleData: Data {
        return Data()
    }
    
    var task: Task {
        switch self {
        case .upload(let image):
            let stringFormData = MultipartFormData(provider: .data("swift".data(using: .utf8)!), name: "folder", mimeType: "text/plain")
            let imageFormData = MultipartFormData(provider: .data(image), name: "photos", fileName: "image.png", mimeType: "image/*")
            return .uploadMultipart([stringFormData, imageFormData])
        }
    }
    
    var headers: [String : String]? {
        return nil
    }
}
