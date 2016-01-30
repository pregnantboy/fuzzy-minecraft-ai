//
//  ViewController.m
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 24/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import "MainMenuViewController.h"

@implementation MainMenuViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self.backgroundImg setAlphaValue:0.90];
    
//    NSLog(@"%@", [[NSFontManager sharedFontManager]availableFontFamilies]);
    /* available fonts: THEMINION, DK Prince Forg, Shablagoo */
    
    [MainMenuViewController addShadow:self.createNewAI];
    [MainMenuViewController addWhiteMenuText:self.createNewAI withSize:27 withText:@"Create New AI"];
    
    [MainMenuViewController addShadow:self.modifyAI];
    [MainMenuViewController addWhiteMenuText:self.modifyAI withSize:27 withText:@"Modify AI"];
    
    [MainMenuViewController addShadow:self.exit];
    [MainMenuViewController addGreyMenuText:self.exit withSize:27 withText:@"Exit"];
    [self.exit setAction:@selector(quitApp)];
    [self.createNewAI setAction:@selector(goToCreatePage)];
    [self.modifyAI setAction:@selector(goToSelectAIPage)];
}

+ (void) addShadow:(NSView *) view {
    NSShadow *dropShadow = [[NSShadow alloc] init];
    [dropShadow setShadowColor:[NSColor blackColor]];
    [dropShadow setShadowOffset:NSMakeSize(5, 10.0)];
    [dropShadow setShadowBlurRadius:10.0];
    [view setShadow: dropShadow];
}

+ (void) addWhiteMenuText:(NSButton*)button withSize:(int)size  withText:(NSString *)text{
    NSFont *txtFont = [NSFont fontWithName:@"DK Prince Frog" size:size];
    NSColor *txtColor = [NSColor whiteColor];
    NSMutableParagraphStyle *centredStyle = [[NSParagraphStyle defaultParagraphStyle] mutableCopy];
    [centredStyle setAlignment:NSCenterTextAlignment];
    NSDictionary *txtDict = @{
                              NSFontAttributeName: txtFont,
                              NSForegroundColorAttributeName: txtColor,
                              NSParagraphStyleAttributeName: centredStyle
                              };
    NSAttributedString *attrStr = [[NSAttributedString alloc] initWithString:text attributes:txtDict];
    [button setAttributedTitle:attrStr];
}

+ (void) addGreyMenuText:(NSButton*)button withSize:(int)size  withText:(NSString *)text{
    NSFont *txtFont = [NSFont fontWithName:@"DK Prince Frog" size:size];
    NSColor *txtColor = [NSColor grayColor];
    NSMutableParagraphStyle *centredStyle = [[NSParagraphStyle defaultParagraphStyle] mutableCopy];
    [centredStyle setAlignment:NSCenterTextAlignment];
    NSDictionary *txtDict = @{
                              NSFontAttributeName: txtFont,
                              NSForegroundColorAttributeName: txtColor,
                              NSParagraphStyleAttributeName: centredStyle
                              };
    NSAttributedString *attrStr = [[NSAttributedString alloc] initWithString:text attributes:txtDict];
    [button setAttributedTitle:attrStr];
}

- (IBAction) quitApp {
    NSLog(@"terminating app");
    [NSApp terminate:self];
}

- (IBAction)goToCreatePage {
    NSStoryboard *mainsb = [NSStoryboard storyboardWithName:@"Main" bundle:[NSBundle mainBundle]];
    NSViewController *createAIVc = [mainsb instantiateControllerWithIdentifier:@"CreateAIViewController"];
    [[createAIVc view] setFrame:self.view.frame];
    [[NSApp mainWindow] setContentViewController:createAIVc];
}

- (IBAction)goToSelectAIPage {
    NSStoryboard *mainsb = [NSStoryboard storyboardWithName:@"Main" bundle:[NSBundle mainBundle]];
    NSViewController *vc = [mainsb instantiateControllerWithIdentifier:@"SelectAIViewController"];
    [[vc view] setFrame:self.view.frame];
    [[NSApp mainWindow] setContentViewController:vc];
}

@end
