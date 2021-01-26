import UIKit

class MainViewController: UIViewController, UINavigationControllerDelegate {
    
    let picker = UIImagePickerController()
    
    let viewModel = MainViewModel()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        picker.delegate = self
        picker.sourceType = .photoLibrary
        
        let button = UIButton()
        button.setTitle("upload", for: .normal)
        button.backgroundColor = .systemRed
        button.translatesAutoresizingMaskIntoConstraints = false
        
        self.view.addSubview(button)
        
        NSLayoutConstraint.activate([
            button.topAnchor.constraint(equalTo: self.view.safeAreaLayoutGuide.topAnchor, constant: 20),
            button.centerXAnchor.constraint(equalTo: self.view.centerXAnchor),
            button.widthAnchor.constraint(equalTo: self.view.widthAnchor, multiplier: 0.33),
            button.heightAnchor.constraint(equalToConstant: 100)
        ])
        
        button.addTarget(self, action: #selector(request(_:)), for: .touchUpInside)
    }
    
    @objc func request(_ sender: UIButton) {
        self.present(picker, animated: false) {
            print("picker presented")
        }
    }
}

extension MainViewController : UIImagePickerControllerDelegate {
    
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        
        guard let selectedImage = info[.originalImage] as? UIImage else {
            fatalError("Expected a dictionary containing an image, but was provided the following: \(info)")
        }
        
        print("이미지: \(selectedImage)")
        
        let imageData = selectedImage.pngData() // .jpegData(compressionQuality: 0.5)
        
        viewModel.uploadImage(imageData: imageData!)
        
        viewModel.uploadImageMoya(imageData: imageData!)
        
        picker.dismiss(animated: false, completion: nil)
    }
    
    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        print("취소")
    }
}

/**
 UIKit 프리뷰
 */
#if DEBUG
import SwiftUI

struct MainViewControllerRepresentable: UIViewControllerRepresentable {
    
    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {/* no-op */}
    
    @available(iOS 13.0, *)
    func makeUIViewController(context: Context) -> some UIViewController {
        return MainViewController()
    }
}

struct MainViewController_Previews: PreviewProvider {
    
    static var previews: some View {
        MainViewControllerRepresentable()
            .previewDisplayName("아이폰 12")
    }
}
#endif
