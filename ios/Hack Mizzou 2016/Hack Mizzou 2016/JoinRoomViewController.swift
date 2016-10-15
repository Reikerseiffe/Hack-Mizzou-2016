//
//  JoinRoomViewController.swift
//  Hack Mizzou 2016
//
//  Created by Reiker Seiffe on 10/14/16.
//  Copyright © 2016 Reiker R Seiffe. All rights reserved.
//

import UIKit

class JoinRoomViewController: UIViewController {
    
    let webWorker = WebWorker()
    
    var songArray:NSArray = []
    var roomID:Int = 0
    
    
    
    @IBOutlet weak var roomInput: UITextField!
    

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func joinRoom(sender: AnyObject) {
        //var roomID = roomInput.text
        
        if let roomID:Int? = Int(roomInput.text!){
            print(roomID!)
            let unwrapperRoom = roomID!
            print("unwrappedRoom: " + String(unwrapperRoom))
            self.roomID = unwrapperRoom
            webWorker.joinRoom(unwrapperRoom){
                (result, error) in
                
                print("Result: ")
                print(result!)
                self.songArray = result!
                dispatch_async(dispatch_get_main_queue()) {
                    self.performSegueWithIdentifier("joinedRoom", sender: self)
                }
            }
        }
        
    }
    

    @IBAction func dismissKeyboard(sender: AnyObject) {
        roomInput.resignFirstResponder()
    }
    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
        
        
        if let controller:SongListViewController = segue.destinationViewController as? SongListViewController{
            controller.songArray = songArray
            controller.roomID = roomID
        }
    }
}
