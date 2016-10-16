//
//  NewSongViewController.swift
//  Hack Mizzou 2016
//
//  Created by Reiker Seiffe on 10/15/16.
//  Copyright © 2016 Reiker R Seiffe. All rights reserved.
//

import UIKit

class NewSongViewController: UIViewController, UISearchBarDelegate {

    @IBOutlet weak var searchBar: UISearchBar!
    @IBOutlet weak var tableView: UITableView!
    let webWorker = WebWorker()
    
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.searchBar.delegate = self
    

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func searchBarSearchButtonClicked(searchBar: UISearchBar) {
        
        //do something
        searchBar.resignFirstResponder() //hide keyboard
        print("Running 1")
        webWorker.searchSong("Hello"){
            (result:NSArray?, error:String?) in
            print(result)
            let unwrapped = result!
            
            //print(unwrapped["tracks"]["items"])
            
            //print(unwrapped.count)
        }
        
        
    }
    
    
    
    
    
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
