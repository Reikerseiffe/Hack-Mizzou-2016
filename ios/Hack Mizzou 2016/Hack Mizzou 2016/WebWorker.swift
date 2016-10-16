//
//  WebWorker.swift
//  Hack Mizzou 2016
//
//  Created by Reiker Seiffe on 10/14/16.
//  Copyright © 2016 Reiker R Seiffe. All rights reserved.
//

import UIKit

class WebWorker: NSObject {

    
    func joinRoom(roomID:Int, completion: (result:NSArray?, error:String?)->Void){
            let url = "http://ec2-52-42-199-153.us-west-2.compute.amazonaws.com/api/getPlaylist" //build a request for a URL
            var post: String = "roomID=\(roomID)"
            
            self.requestHandler(url, post: post){
                (result:NSArray?, error:String?) in
                if let result = result{
                    //print("got a result: " + String(result));
                    completion(result:result, error:error)
                }
            }
    }
    
    func upvote(roomID:Int, index:Int){
        print("room id: " + String(roomID))
        print("index: " + String(index))
        let url = "http://ec2-52-42-199-153.us-west-2.compute.amazonaws.com/api/upvote" //build a request for a URL
        let post = "roomID=\(roomID)&songID=\(index)"
        print("Post: " + post)
        let postData: NSData = post.dataUsingEncoding(NSASCIIStringEncoding, allowLossyConversion: true)!
        let postLength: String = "\(postData.length)"
        let request: NSMutableURLRequest = NSMutableURLRequest()
        request.URL = NSURL(string: url)
        request.HTTPMethod = "POST"
        request.setValue(postLength, forHTTPHeaderField: "Content-Length")
        request.setValue("application/x-www-form-urlencoded", forHTTPHeaderField: "Content-Type")
        request.HTTPBody = postData
        
        let task = NSURLSession.sharedSession().dataTaskWithRequest(request) {
            (let data, let response, let error) in
            //print("Ran the request and the data was: \(data)")
            if (data != nil) {
                //print(data)
                //print("Ran the request and the data was: \(data)")
            }
        
        }
        
        task.resume()// run the call
    }
    
    func downvote(roomID:Int, index:Int){
    print("room id: " + String(roomID))
    print("index: " + String(index))
    let url = "http://ec2-52-42-199-153.us-west-2.compute.amazonaws.com/api/downvote" //build a request for a URL
    let post = "roomID=\(roomID)&songID=\(index)"
    print("Post: " + post)
    let postData: NSData = post.dataUsingEncoding(NSASCIIStringEncoding, allowLossyConversion: true)!
    let postLength: String = "\(postData.length)"
    let request: NSMutableURLRequest = NSMutableURLRequest()
    request.URL = NSURL(string: url)
    request.HTTPMethod = "POST"
    request.setValue(postLength, forHTTPHeaderField: "Content-Length")
    request.setValue("application/x-www-form-urlencoded", forHTTPHeaderField: "Content-Type")
    request.HTTPBody = postData
    
    let task = NSURLSession.sharedSession().dataTaskWithRequest(request) {
    (let data, let response, let error) in
    //print("Ran the request and the data was: \(data)")
    if (data != nil) {
    print(data)
    //print("Ran the request and the data was: \(data)")
    }
    
    }
    
    task.resume()// run the call
    }

    
    
    func searchSong(track:String, completion: (result:NSArray?, error:String?)->Void){
        
        let url = NSURL(string: "https://api.spotify.com/v1/search?q=\(track)&type=track&market=US")
        print(url!)
        
        let task = NSURLSession.sharedSession().dataTaskWithURL(url!) {(data, response, error) in
            print(NSString(data: data!, encoding: NSUTF8StringEncoding))
            
            if (data != nil) {
                //print("Ran the request and the data was: \(data)")
                self.parseJSON(data!){
                    (result:NSArray?, error:String?) in
                    print("Got the JSON array back 2: \(result)")
                    completion(result:result, error:error)
                }
            }else{
                print("Data was nil")
                completion(result:nil, error:String(error))
                
            }
            
        }
        
        task.resume()
        
        
        
        /*let url = "https://api.spotify.com/v1/search?q=\(track)&type=track&market=US" //build a request for a URL
        
        print(url)
        
        self.spotifyRequestHandler(url, post: ""){
            (result:NSArray?, error:String?) in
            if let result = result{
                print("got a result: " + String(result));
                completion(result:result, error:error)
            }
        }*/
    }
    
    
    func spotifyRequestHandler(url:String, post:String, completion: (result:NSArray?, error:String?)->Void){
        //let postData: NSData = post.dataUsingEncoding(NSASCIIStringEncoding, allowLossyConversion: true)!
        //let postLength: String = "\(postData.length)"
        //let request: NSMutableURLRequest = NSMutableURLRequest()
        //request.URL = NSURL(string: url)
        //request.HTTPMethod = "GET"
        //request.setValue(postLength, forHTTPHeaderField: "Content-Length")
        //request.setValue("application/x-www-form-urlencoded", forHTTPHeaderField: "Content-Type")
        //request.HTTPBody = postData
        
        let url = NSURL(string:url)
        
        //let task = NSURLSession.sharedSession().dataTaskWithURL(url!) {(data, response, error) in
           // print(NSString(data: data!, encoding: NSUTF8StringEncoding))
        //}
        
        //task.resume()
        
        let task = NSURLSession.sharedSession().dataTaskWithURL(url!) {
            (let data, let response, let error) in
            //print("Ran the request and the data was: \(data)")
            if (data != nil) {
                //print("Ran the request and the data was: \(data)")
                self.parseJSON(data!){
                    (result:NSArray?, error:String?) in
                    print("Got the JSON array back 1: \(result)")
                    completion(result:result, error:error)
                }
            }else{
                print("Data was nil")
                completion(result:nil, error:String(error))
            }
            
        }
        
        task.resume()// run the call
        
    }

    
    
    

    func requestHandler(url:String, post: String, completion: (result:NSArray?, error:String?)->Void){
        let postData: NSData = post.dataUsingEncoding(NSASCIIStringEncoding, allowLossyConversion: true)!
        let postLength: String = "\(postData.length)"
        let request: NSMutableURLRequest = NSMutableURLRequest()
        request.URL = NSURL(string: url)
        request.HTTPMethod = "POST"
        request.setValue(postLength, forHTTPHeaderField: "Content-Length")
        request.setValue("application/x-www-form-urlencoded", forHTTPHeaderField: "Content-Type")
        request.HTTPBody = postData
        
        let task = NSURLSession.sharedSession().dataTaskWithRequest(request) {
            (let data, let response, let error) in
            //print("Ran the request and the data was: \(data)")
            if (data != nil) {
                //print("Ran the request and the data was: \(data)")
                self.parseJSON(data!){
                    (result:NSArray?, error:String?) in
                    //print("Got the JSON array back: \(result)")
                    completion(result:result, error:error)
                }
            }else{
                print("Data was nil")
                completion(result:nil, error:String(error))
            }
            
        }
        
        task.resume()// run the call    
        
    }
    
    /*func requestHandler(request: NSMutableURLRequest, message: NSString, completion: (result:NSArray?, error:String?)->Void){
        request.HTTPMethod = "POST" //set is to be a post request
        request.HTTPBody = message.dataUsingEncoding(NSUTF8StringEncoding)
        
        
        let task = NSURLSession.sharedSession().dataTaskWithRequest(request) {
            (let data, let response, let error) in
            //print("Ran the request and the data was: \(data)")
            if (data != nil) {
                //print("Ran the request and the data was: \(data)")
                self.parseJSON(data!){
                    (result:NSArray?, error:String?) in
                    print("Got the JSON array back: \(result)")
                    completion(result:result, error:error)
                }
            }else{
                print("Data was nil")
                completion(result:nil, error:String(error))
            }
            
        }
        
        task.resume()// run the call
    }*/
    
    
    
    func parseJSON(jsonData: NSData, completion: (result:NSArray?, error:String?)->Void) -> Void{
        //print("parse json called")
        //let dataString = String(data: jsonData, encoding: NSUTF8StringEncoding)
        //print("JSON data: " + dataString!)
        var jsonResultWrapped: NSArray?//array variable
        do {//do try catch
            jsonResultWrapped = try NSJSONSerialization.JSONObjectWithData(jsonData, options: .MutableContainers) as? NSArray//casts as NSDictionary
        } catch let caught as NSError {
            
            print("\(caught)")//print the error
            print("parseJSON completion -> error")
            completion(result:nil,error:String(caught))
        }
        //print("\(jsonResultWrapped)")
        if let jsonResult = jsonResultWrapped {//unwrap the optional
            print("parseJSON completion -> success")
            completion(result:jsonResult, error:nil)
            
        }
    }
}

    

