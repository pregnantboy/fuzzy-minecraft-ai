//
//  ViewController.h
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 24/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import <Cocoa/Cocoa.h>

@interface MainMenuViewController : NSViewController

@property (weak) IBOutlet NSImageView *backgroundImg;
@property (weak) IBOutlet NSButton *createNewAI;
@property (weak) IBOutlet NSButton *modifyAI;
@property (weak) IBOutlet NSButton *exit;

+ (void) addShadow:(NSView *) view;
+ (void) addWhiteMenuText:(NSButton*)button withSize:(int)size  withText:(NSString *)text;
+ (void) addGreyMenuText:(NSButton*)button withSize:(int)size  withText:(NSString *)text;
@end

